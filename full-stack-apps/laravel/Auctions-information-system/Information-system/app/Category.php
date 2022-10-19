<?php

namespace App;

use Illuminate\Support\Str;
use Illuminate\Database\Eloquent\Model;

class Category extends Model
{
    protected $table = 'categories';

    public function path() 
    {
        return url("/category/{$this->id}-" . Str::slug($this->title));
    }
    
    public function auctions()
    {   
        return $this->hasMany('App\Auction');
    }
}
