<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Auction;
use App\User;
use App\Category;
use App\Picture;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\File;
use Carbon\Carbon;
use DateInterval;

class AuctionController extends Controller
{
    /**
     * Returns accepted auctions (with auctioneer)
     *
     */

    public function index()
    {
        $auctions = Auction::whereNotNull('auctioneer_id')->get();
        $auction_list = array();
        foreach($auctions as $auction) 
        {
            if(!$auction->ended()) {
                array_push($auction_list, $auction);
            }
        }
        return view('website.auctions.index')->with(compact('auction_list'));
    }
    
    public function new()
    {
        $now = Carbon::now()->add(new DateInterval('PT1H'));
        $date = Carbon::parse($now)->toDateString() . ' ' .Carbon::parse($now)->toTimeString();
        $auctions = Auction::whereNotNull('start_at')->whereDate('start_at','<', $date)->OrderBy('start_at','asc')->take(10)->get();
        $auction_list = array();
        foreach($auctions as $auction) 
        {
            if(!$auction->ended()) {
                array_push($auction_list, $auction);
            }
        }
        return view('website.auctions.index')->with(compact('auction_list'));
    }
    
    public function ending()
    {
        $now = Carbon::now()->add(new DateInterval('PT1H'));
        $date = Carbon::parse($now)->toDateString() . ' ' .Carbon::parse($now)->toTimeString();
        $auctions = Auction::whereNotNull('end_at')->whereDate('end_at','>', $date)->OrderBy('end_at','asc')->take(10)->get();
        $auction_list = array();
        foreach($auctions as $auction) 
        {
            if($auction->running()) {
                array_push($auction_list, $auction);
            }
        }
        return view('website.auctions.index')->with(compact('auction_list'));
    }
    
    public function upcoming()
    {
        $now = Carbon::now()->add(new DateInterval('PT1H'));
        $date = Carbon::parse($now)->toDateString() . ' ' .Carbon::parse($now)->toTimeString();
        $auction_list = Auction::whereNotNull('start_at')->whereDate('start_at','>', $date)->OrderBy('start_at','desc')->take(10)->get();
        return view('website.auctions.index')->with(compact('auction_list'));
    }
    
    public function approved()
    {
        $user = Auth::user();
        $auctions = Auction::where('auctioneer_id',$user->id)->get();
        $auction_list = array();
        foreach($auctions as $auction) 
        {
            if(!$auction->ended()) {
                array_push($auction_list, $auction);
            }
        }
        return view('website.auctions.index')->with(compact('auction_list'));
    }

    public function my_auctions()
    {
        $user = Auth::user();
        $auction_list = Auction::where('author_id',$user->id)->get();
        return view('website.auctions.index')->with(compact('auction_list'));
    }


    public function unapproved()
    {
        $auction_list = Auction::whereNull('auctioneer_id')->get();
        return view('website.auctions.index')->with(compact('auction_list'));
    }

    /**
     * Returns accepted auctions by category
     *
     */
    public function category($category_id, $category_slug)
    {   
        $auctions = Auction::whereNotNull('auctioneer_id')->where('category_id', $category_id)->get();
        $auction_list = array();
        foreach($auctions as $auction) 
        {
            if(!$auction->ended()) {
                array_push($auction_list, $auction);
            }
        }
        return view('website.auctions.index')->with(compact('auction_list'));
    }

    
    
    
    
    // TODO: fetch hot auctions function, new auctions and ending auctions

    /**
     * Returns auction by id
     *
     */

    public function show($auction_id)
    {
        $auction = Auction::findOrFail($auction_id);
        return view('website.auctions.show')->with(compact('auction'));
    }

    /**
     * Returns create view
     *
     */

    public function create()
    {
        $category_list = Category::get();
        return view('website.auctions.create')->with(compact('category_list'));
    }

    /**
     * Stores created record
     *
     */

    public function store($author_id)
    {
        $author = User::findOrFail($author_id);
        
        request()->validate([
            'title' => 'required|max:255',
            'category' => 'required|numeric',
            'type' => 'required|max:255',
            'rule' => 'required|max:255',
            'description' => 'required',
            'min_cost' => 'required|numeric',
            'max_cost' => 'required|numeric',
        ]);

        if (request('min_cost') >= request('max_cost')) {
            Session::flash('custom_message', 'Nesprávné hodnoty ceny');
            return redirect()->back();
        }

        $category = Category::findOrFail(request('category'));
        $auction = new Auction();
        $auction->author_id = $author->id;
        $auction->title = request('title');
        $auction->category_id = $category->id;
        $auction->type = request('type');
        $auction->rule = request('rule');
        $auction->description = request('description');
        $auction->min_cost = request('min_cost');
        $auction->max_cost = request('max_cost');
        $auction->save();

        File::makeDirectory('storage/auctions/' . $auction->id, $mode = 0777, true, true);

        return redirect('/auctions/' . $auction->id . '/edit');
    }

    /**
     * Returns edit view
     *
     */
    
    public function edit($auction_id)
    {
        $auction = Auction::findOrFail($auction_id);
        $category_list = Category::get();
        $pictures = $auction->pictures;
        return view('website.auctions.edit')->with(compact('auction', 'pictures', 'category_list'));
    }

    public function confirm($auction_id)
    {
        $auction = Auction::findOrFail($auction_id);
        if ($auction->author_id == Auth::user()->id) {
            Session::flash('custom_message', 'Nemůžete schválit vlastní aukci');
            return redirect()->back();
        }
        $pictures = $auction->pictures;
        return view('website.auctions.confirm')->with(compact('auction', 'pictures'));
    }

    /**
     * Stores edited record
     *
     */

    public function update($auction_id)
    {
        request()->validate([
            'title' => 'required|max:255',
            'category' => 'required|numeric',
            'type' => 'required|max:255',
            'rule' => 'required|max:255',
            'description' => 'required',
            'min_cost' => 'required|numeric',
            'max_cost' => 'required|numeric',
        ]);

        if (request('min_cost') >= request('max_cost')) {
            Session::flash('custom_message', 'Nesprávné hodnoty ceny');
            return redirect()->back();
        }

        $category = Category::findOrFail(request('category'));
        $auction = Auction::findOrFail($auction_id);
        $auction->title = request('title');
        $auction->category_id = $category->id;
        $auction->type = request('type');
        $auction->rule = request('rule');
        $auction->description = request('description');
        $auction->min_cost = request('min_cost');
        $auction->max_cost = request('max_cost');
        $auction->save();

        return redirect('/auctions/'.$auction->id);
    }

    /**
     * Deletes record
     *
     */

    public function destroy($auction_id)
    {
        $auction = Auction::findOrFail($auction_id);
        $pictures = $auction->pictures;
        if($pictures != NULL) {
            foreach($pictures as $picture) {
                if(File::exists(storage_path('app/public/auctions/' . $auction->id . '/' . $picture->file_name))){
                    File::delete(storage_path('app/public/auctions/' . $auction->id . '/' . $picture->file_name));
                }
                $picture->delete();
            }
        }
        File::deleteDirectory(storage_path('app/public/auctions/' . $auction->id));
        $auction->delete();
        return redirect('/');
    }


    /**
     * Returns accepted auctions by category and name
     *
     */

    public function search_auction()
    {   
        if (request('category') == "0") {
            $auction_list = Auction::whereNotNull('auctioneer_id')->where('title', 'like', '%' . request('text') . '%')->get();
        } else {
            $auction_list = Auction::whereNotNull('auctioneer_id')->where('category_id', request('category'))->where('title', 'like', '%' . request('text') . '%')->get();
        }
        return view('website.auctions.index')->with(compact('auction_list'));
    }


    public function sign_up($auction_id)
    {
        $user = Auth::user();
        $auction = Auction::findOrFail($auction_id);
        if ($auction->users->contains($user)) {
            Session::flash('custom_message', 'Už jste byli přihlášeni');
            return redirect()->back();
        } else {
            $auction->users()->attach($user);
            Session::flash('custom_message', 'Přihlášení proběhlo úspěšně');
            return redirect()->back();
        }
    }

    public function signed_users($auction_id)
    {
        $auction = Auction::findOrFail($auction_id);
        $users = $auction->users;
        return view('website.users.index_registered')->with(compact('users','auction'));
    }

    public function approve($auction_id)
    {
        $auction = Auction::findOrFail($auction_id);
        $user = Auth::user();
        
        request()->validate([
            'start_at' => 'required|date',
            'end_at' => 'required|date',
        ]);
        if (request('start_at') < now()->add(new DateInterval('PT1H'))) {
            Session::flash('custom_message', 'Datum záčátku musí být větší než dnešní.');
            return redirect()->back();
        }


        $auction->start_at = request('start_at');
        $auction->end_at = request('end_at');
        $auction->auctioneer_id = $user->id;
        $auction->save();
        return redirect('/auctions/' . $auction->id);
    }

    public function results()
    {
        $auctions = Auction::whereNotNull('auctioneer_id')->get();
        $user = Auth::user();
        $auction_list = array();
        foreach($auctions as $auction) 
        {
            if($auction->ended() && ($auction->auctioneer_id == $user->id || $auction->author_id == $user->id || $auction->users->contains($user))) {
                array_push($auction_list, $auction);
            }
        }
        return view('website.auctions.index_results')->with(compact('auction_list'));
    }

}
