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
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Session;
use Image;
use App\Dog;

class DogController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth');
    }

    public function index()
    {
        $dogs = Dog::orderBy('id', 'desc')->paginate(10);
        return view('admin.dogs.index')->with(compact('dogs'));
    }

    public function create()
    {
        return view('admin.dogs.create');
    }

    public function store()
    {
        $data = request()->validate([
            'name' => 'required|max:150',
            'breed' => 'required|max:150',
            'age' => 'required|numeric',
            'description' => 'required',
            'photo' => 'required|image|mimes:jpeg,jpg,png,gif|max:10000',
        ]);

        $dog = new Dog();

        $dog->name = request('name');
        $dog->breed = request('breed');
        $dog->age = request('age');
        $dog->description = request('description');
        
        $dog->save();
        
        $image_name = $dog->id . '.jpg';
        $image_upload = Image::make(request('photo'));
        $image_upload->orientate();
        $image_upload->fit(400,400);
        $image_upload->save('storage/dogs/' . $image_name);


        Session::flash('custom_message', 'Pes byl přidán.'); 
        return redirect('/admin/dogs/' . $dog->id . '/edit');
    }

    public function edit($id)
    {
        $dog = Dog::findOrFail($id);
        return view('admin.dogs.edit')->with(compact('dog'));
    }

    public function update($id)
    {
        $data = request()->validate([
            'name' => 'required|max:150',
            'breed' => 'required|max:150',
            'age' => 'required|numeric',
            'description' => 'required',
            'photo' => 'required|image|mimes:jpeg,jpg,png,gif|max:10000',
        ]);
        
        $dog = Dog::find($id);

        $dog->name = request('name');
        $dog->breed = request('breed');
        $dog->age = request('age');
        $dog->description = request('description');
        
        $dog->save();
        

        if(request('photo')) {
            $image_name = $dog->id . '.jpg';
            if(File::exists('storage/dogs/' . $image_name)){
                File::delete('storage/dogs/' . $image_name);
            }
            $image_upload = Image::make(request('photo'));
            $image_upload->orientate();
            $image_upload->fit(400,400);
            $image_upload->save('storage/dogs/' . $image_name);
        }

        Session::flash('custom_message', 'Informace byly uloženy.'); 
        return redirect('/admin/dogs/' . $dog->id . '/edit');
    }

    public function destroy($id)
    {
        $dog = Dog::findOrFail($id);
        $image_name = $dog->id . '.jpg';
        if(File::exists('storage/dogs/' . $image_name)){
            File::delete('storage/dogs/' . $image_name);
        }
        $dog->delete();
        
        Session::flash('custom_message', 'Pes byl odstraněn ze seznamu.'); 
        return redirect('/admin/dogs');
    }
}
