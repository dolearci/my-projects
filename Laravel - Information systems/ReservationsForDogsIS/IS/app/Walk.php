<?php

/*
|-------------------------------------------------
| Autor: Daniel Olearčin (xolear00)
|-------------------------------------------------
|
*/

namespace App;

use Illuminate\Database\Eloquent\Model;

class Walk extends Model
{
    public function dog()
    {
        return $this->belongsTo('App\Dog');
    }
}
