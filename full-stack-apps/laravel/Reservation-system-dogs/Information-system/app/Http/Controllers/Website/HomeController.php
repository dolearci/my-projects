<?php

/*
|-------------------------------------------------
| Autor: Daniel Havranek (xhavra18)
|-------------------------------------------------
|
*/

namespace App\Http\Controllers\Website;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Dog;
use App\Walk;

class HomeController extends Controller
{
    public function index() 
    {
        return view('website.home');
    }

    public function unreserved(Request $request) 
    {
        $dog_list = Dog::get();
        $reserved_dogs = array();
        
        if (request('day') == 'today') {
            $date = date('Y-m-d');
        } else if (request('day') == 'tomorow') {
            $date = date("Y-m-d", time() + 86400) ;
        } else {
            $date = date("Y-m-d", time() + 172800);
        }
        
        $walks = Walk::whereDate('date_time', $date)->get();

        foreach ($walks as $walk) {
            array_push($reserved_dogs, $walk->dog->id);
        }

        $records = '';

        $date_time = $date . ' ' . request('time') . ':00:00';

        foreach ($dog_list as $dog) {
            if (!in_array($dog->id, $reserved_dogs)) {
                $records .= '<div class="col-12 col-sm-6 col-md-4 col-lg-3">
                <div class="card shadow">
                    <img class="card-img-top" src="' . asset('storage/dogs/' . $dog->id . '.jpg') . '" alt="Card image" style="width:100%">
                    <div class="card-body">
                    <h4 class="card-title">' . $dog->name . '</h4>
                    <p class="card-text"><b>Plemeno:</b> ' . $dog->breed . '<br>
                        <b>Vek:</b> ' . $dog->age . '<br>
                        ' . $dog->description . '</p>
                        <form action="/confirm" method="GET">
                            <input type="hidden" name="date_time" value="' . $date_time . '"/>
                            <input type="hidden" name="dog_id" value="' . $dog->id . '"/>
                            <button type="submit" class="btn btn-custom">Vybrať</button>
                        </form>
                    </div>
                </div>
            </div>';
            }
        }

        return response()->json(array('records' => $records));
    }

    public function confirm() 
    {
        $dog_id = request('dog_id');
        $date_time = request('date_time');
        return view('website.confirm')->with(compact('dog_id', 'date_time'));
    }

    public function info() 
    {
        return view('website.info');
    }
}
