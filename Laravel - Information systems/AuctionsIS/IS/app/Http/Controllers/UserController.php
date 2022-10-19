<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Session;
use App\User;
use App\Offer;
use App\Auction;
use App\AuctionResult;
//namespace App\Http\Controllers\AuctionController;
use Gate;

class UserController extends Controller
{
    
    public function index()
    {
        $users = User::orderBy('id', 'desc')->paginate(10);
        return view('website.users.index')->with(compact('users'));
    }

    public function show($id)
    {
        $user = User::findOrFail($id);
        return view('website.users.show')->with(compact('user'));
    }

    public function edit($id)
    {
        $user = User::findOrFail($id);

        return view('website.users.edit')->with(compact('user'));
    }

    public function update($id)
    {
        $data = request()->validate([
            'name' => 'required|string|max:191',
            'email' => 'required|string|email|max:191',
            'role' => 'required|string|max:191',
        ]);

        $user = User::findOrFail($id);
        $user->role = request('role');
        $user->name = request('name');
        $user->email = request('email');
        $user->save();

        Session::flash('custom_message', 'Uživatel byl uložen.'); 
        return redirect('/users');
    }

    public function destroy($id)
    {
        $user = User::findOrFail($id);
        $auctioneer = Auction::where('auctioneer_id', $user->id)->get();
        $auctions = Auction::where('author_id', $user->id)->get();
        $offers = Offer::where('user_id', $user->id)->get();
        $winners = AuctionResult::where('winner_id', $user->id)->get();
        if ($auctioneer->isEmpty() && $auctions->isEmpty()) {
            $user->delete();
            Session::flash('custom_message', 'Uživatel byl odstraněn.'); 
        } else {
            foreach($offers as $offer)
            {
                $offer->user_id = 66666; // deleted user
                $offer->save();
            }
            foreach($winners as $winner)
            {
                $winner->winner_id = 66666; // deleted user
                $winner->save();
            }
            foreach($auctions as $auction)
            {
                $auction->author_id = 66666; // deleted user
                $auction->save();
            }
            $user->delete();
            Session::flash('custom_message', 'Uživatel byl odstraněn.'); 
        }
        
        return redirect('/users');
    }

    public function confirm($auction_id, $user_id)
    {
        $user = User::findOrFail($user_id);
        $auction = Auction::findOrFail($auction_id);
        $auction->users()->updateExistingPivot($user, ['status' => 'registered']);

        return redirect()->back();
    }

    public function unconfirm($auction_id, $user_id)
    {
        $user = User::findOrFail($user_id);
        $auction = Auction::findOrFail($auction_id);
        $auction->users()->updateExistingPivot($user, ['status' => 'unregistered']);

        return redirect()->back();
    }
}
