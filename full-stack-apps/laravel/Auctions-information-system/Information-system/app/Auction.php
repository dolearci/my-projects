<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use App\Offer;
use App\Picture;
use App\User;
use DateInterval;

class Auction extends Model
{
    protected $fillable = [
        'name', 'tag', 'type',
        'rule', 'description',
        'min_cost', 'max_cost,'
    ];

    protected $casts = [
        'start_at' => 'datetime',
        'end_at' => 'datetime',
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
    ];

    public function author()
    {   
        return $this->belongsTo('App\User');
    }

    public function auctioneer()
    {   
        return $this->belongsTo('App\User');
    }

    public function category()
    {   
        return $this->belongsTo('App\Category');
    }

    public function offers()
    {   
        return $this->hasMany('App\Offer');
    }

    public function users()
    {   
        return $this->belongsToMany('App\User', 'registered_at_auctions')->withPivot('status')->withTimestamps();
    }

    public function pictures()
    {
        return $this->hasMany('App\Picture');
    }

    public function top_bid() 
    {
        $offer = Offer::where('auction_id', $this->id)->orderBy('bid', 'desc')->first();
        if(!$offer) {
            return $this->min_cost;
        } else {
            return $offer->bid;
        }
    }

    public function lowest_bid() 
    {
        $offer = Offer::where('auction_id', $this->id)->orderBy('bid', 'asc')->first();
        if(!$offer) {
            return $this->max_cost;
        } else {
            return $offer->bid;
        }
    }

    public function thumbnail()
    {
        $picture = Picture::where('auction_id', $this->id)->first();
        return $picture;
    }

    public function running()
    {
        if($this->end_at < now()->add(new DateInterval('PT1H')) || $this->start_at > now()->add(new DateInterval('PT1H'))) {
            return false;
        }
        return true;
    }

    public function ended()
    {   
        if ($this->type == "poptávková") {
            if($this->end_at < now()->add(new DateInterval('PT1H')) || $this->top_bid() == $this->max_cost) {
                return true;
            }
        } else {
            if($this->end_at < now()->add(new DateInterval('PT1H')) || $this->lowest_bid() == $this->min_cost) {
                return true;
            }
        }
        return false;
    }

    public function winner()
    {
        if ($this->type == "poptávková") {
            $offer = Offer::where('auction_id', $this->id)->orderBy('bid', 'desc')->first();
        } else {
            $offer = Offer::where('auction_id', $this->id)->orderBy('bid', 'asc')->first();
        }
        $user = User::findOrFail($offer->user_id);
        return $user;
    }
}
