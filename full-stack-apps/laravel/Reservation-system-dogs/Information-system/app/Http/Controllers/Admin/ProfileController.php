<?php

/*
|-------------------------------------------------
| Autor: Daniel Havranek (xhavra18)
|-------------------------------------------------
|
*/

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\User;
use Illuminate\Support\Facades\Session;

class ProfileController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $user = Auth::user();
        return view('admin.profile')->with(compact('user'));
    }

    public function update(Request $request, $id)
    {
        $data = request()->validate([
            'name' => 'required|string|max:191',
            'email' => 'required|string|email|max:191',
        ]);
        
        $user = User::findOrFail($id);
        $user->name = $request->name;
        $user->email = $request->email;
        $user->save();

        Session::flash('custom_message', 'Profil byl uložen.'); 
        return redirect('/admin/profile');
    }
}
