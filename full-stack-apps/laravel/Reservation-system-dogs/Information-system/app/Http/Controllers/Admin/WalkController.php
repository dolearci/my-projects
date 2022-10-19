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
use App\Walk;
use App\Dog;

class WalkController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $walks = Walk::orderBy('id', 'desc')->paginate(10);
        return view('admin.walks.index')->with(compact('walks'));
    }

    public function create()
    {
        $dogs = Dog::orderBy('id', 'desc')->get();
        return view('admin.walks.create')->with(compact('dogs'));
    }

    public function store()
    {
        request()->validate([
            'name' => 'required|max:255',
            'email' => 'required|email|max:255',
            'address' => 'required|max:255',
            'phone' => 'required|max:255',
            'date_time' => 'required|date',
            'dog_id' => 'required|numeric',
        ]);

        $dog = Dog::findOrFail(request('dog_id'));

        $walk = new Walk();

        $walk->name = request('name');
        $walk->email = request('email');
        $walk->address = request('address');
        $walk->phone = request('phone');
        $walk->date_time = request('date_time');
        $walk->dog_id = $dog->id;
        
        $walk->save();


        Session::flash('custom_message', 'Venčenie bolo pridané.'); 
        return redirect('/admin/walks/' . $walk->id . '/edit');
    }

    public function edit($id)
    {
        $walk = Walk::findOrFail($id);
        $dogs = Dog::orderBy('id', 'desc')->get();
        return view('admin.walks.edit')->with(compact('walk', 'dogs'));
    }

    public function update($id)
    {
        request()->validate([
            'name' => 'required|max:255',
            'email' => 'required|email|max:255',
            'address' => 'required|max:255',
            'phone' => 'required|max:255',
            'date_time' => 'required|date',
            'dog_id' => 'required|numeric',
        ]);

        $dog = Dog::findOrFail(request('dog_id'));

        $walk = Walk::find($id);

        $walk->name = request('name');
        $walk->email = request('email');
        $walk->address = request('address');
        $walk->phone = request('phone');
        $walk->date_time = request('date_time');
        $walk->dog_id = $dog->id;
        
        $walk->save();

        Session::flash('custom_message', 'Informace byly uloženy.'); 
        return redirect('/admin/walks/' . $dog->id . '/edit');
    }

    public function destroy($id)
    {
        $walk = Walk::findOrFail($id);
        $walk->delete();
        
        Session::flash('custom_message', 'Venčenie bolo odstraněno.'); 
        return redirect('/admin/walks');
    }
}
