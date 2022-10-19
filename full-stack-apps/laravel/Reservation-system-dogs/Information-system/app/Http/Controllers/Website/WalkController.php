<?php

/*
|-------------------------------------------------
| Autor: Daniel Havranek (xhavra18)
|-------------------------------------------------
|
*/

namespace App\Http\Controllers\Website;

use App\Http\Controllers\Controller;
use App\Mail\ConfirmMail;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Mail;
use Illuminate\Http\Request;
use App\Walk;

class WalkController extends Controller
{
    public function store() 
    {
        $data = request()->validate([
            'name' => 'required|max:255',
            'email' => 'required|email|max:255',
            'address' => 'required|max:255',
            'phone' => 'required|max:255',
            'dog_id' => 'required',
            'date_time' => 'required',
        ]);

        // TODO: phone validation

        $walk = new Walk();
        $walk->dog_id = request('dog_id');
        $walk->date_time = request('date_time');
        $walk->name = request('name');
        $walk->email = request('email');
        $walk->address = request('address');
        $walk->phone = request('phone');
        $walk->save();

        mail::to(request('email'))->send(new ConfirmMail($data));

        Session::flash('custom_message', 'Na email vam bola poslaná informačná zpráva s údajmi.'); 

        return redirect('/');
    }
}
