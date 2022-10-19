<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Picture extends Model
{
    public function auction()
    {
        return $this->belongsTo('App\Auction');
    }
}
