<?php

namespace App\Http\Controllers;

use App\Auction;
use App\Offer;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Session;

class OfferController extends Controller
{
    /**
     * Stores bid
     *
     */

    public function store($auction_id)
    {   
        $auction = Auction::findOrFail($auction_id);
        $user = Auth::user();
        request()->validate([
            'bid' => 'required|numeric',
        ]);
        if(!$auction->running()) {
            Session::flash('custom_message', 'Aukce právě teď neprobíhá'); 
            return redirect()->back();
        }
        if($user->id == $auction->auctioneer->id || $user->id == $auction->author->id) {
            Session::flash('custom_message', 'V této aukci nemůžete přihazovat.'); 
            return redirect()->back();
        }
        if(!$auction->users->contains($user)) {
            Session::flash('custom_message', 'Nejste přihlášení do aukce'); 
            return redirect()->back();
        }
        if($auction->users()->where('user_id', '=', $user->id)->first()->pivot->status == 'unregistered') {
            Session::flash('custom_message', 'Nejste schválení organizátorem'); 
            return redirect()->back();
        }

        if($auction->rule == "uzavřená")
        {   
            // muze se prihodit jen jednou
            $offers = Offer::where('auction_id',$auction->id)->where('user_id',$user->id)->get();
            if($offers->isEmpty())
            {
                // muze prihodit (zatim nema nabidku)
                if(request('bid') >= $auction->min_cost && request('bid') <= $auction->max_cost)
                {
                    // musi byt nejmin min_cost
                    $offer = new Offer();
                    $offer->user_id = $user->id;
                    $offer->auction_id = $auction->id;
                    $offer->bid = request('bid');
                    $offer->save();
                    Session::flash('custom_message', 'Vaše částka bola prihodzena');
                } else {
                    Session::flash('custom_message', 'Bid není v korektnim intervalu'); 
                }
            } else {
                Session::flash('custom_message', 'Táto aukce je uzavřená a vy ste už uskutočnili svuj příhoz. ');
            }
        } else {
            if ($auction->type == "poptávková") {
                if (request('bid') > $auction->top_bid() && request('bid') <= $auction->max_cost) {
                    // musi byt nejmin min_cost
                    $offer = new Offer();
                    $offer->user_id = $user->id;
                    $offer->auction_id = $auction->id;
                    $offer->bid = request('bid');
                    $offer->save();
                    Session::flash('custom_message', 'Vaše částka bola prihodzena');
                } else {
                    Session::flash('custom_message', 'Bid není v korektnim intervalu'); 
                }
            } else {
                if (request('bid') < $auction->lowest_bid() && request('bid') >= $auction->min_cost) {
                    // musi byt nejmin min_cost
                    $offer = new Offer();
                    $offer->user_id = $user->id;
                    $offer->auction_id = $auction->id;
                    $offer->bid = request('bid');
                    $offer->save();
                    Session::flash('custom_message', 'Vaše částka bola prihodzena');
                } else {
                    Session::flash('custom_message', 'Bid není v korektnim intervalu'); 
                }
            }
            
        }
        
        return redirect()->back();
    }

    public function destroy($offer_id)
    {
        $offer = Offer::findOrFail($offer_id);
        $offer->delete();
        return redirect()->back();
    }
}
