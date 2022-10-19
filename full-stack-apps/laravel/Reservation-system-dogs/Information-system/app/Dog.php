<?php

/*
|-------------------------------------------------
| Autor: Daniel Olearčin (xolear00)
|-------------------------------------------------
|
*/

namespace App;

use Illuminate\Database\Eloquent\Model;

class Dog extends Model
{
    public function walks()
    {
        return $this->hasMany('App\Walk');
    }
}
