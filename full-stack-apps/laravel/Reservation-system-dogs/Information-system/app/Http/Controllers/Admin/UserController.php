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
use Illuminate\Support\Facades\Session;
use App\User;

class UserController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $users = User::orderBy('id', 'desc')->paginate(10);
        return view('admin.users.index')->with(compact('users'));
    }

    public function create()
    {
        return view('auth.register');
    }

    public function edit($id)
    {
        $user = User::findOrFail($id);

        return view('admin.users.edit')->with(compact('user'));
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

        Session::flash('custom_message', 'Uživatel byl upraven.'); 
        return redirect('/admin/users');
    }

    public function destroy($id)
    {
        $user = User::findOrFail($id);
        $user->delete();
        
        Session::flash('custom_message', 'Uživatel byl odstraněn.'); 
        return redirect('/admin/users');
    }
}
