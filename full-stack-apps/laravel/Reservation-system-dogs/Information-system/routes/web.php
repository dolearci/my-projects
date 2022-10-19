<?php

/*
|-------------------------------------------------
| Autor: Nina Štefeková (xstefe11)
|-------------------------------------------------
|
*/

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', 'Website\HomeController@index')->name('home');


/*
|-------------------------------------------------
| Artisan commands 
|-------------------------------------------------
|
*/

Route::get('/cache-clear', function() {
    $exitCode = Artisan::call('cache:clear');
    return '<h1>cache:clear done</h1>';
});

Route::get('/config-clear', function() {
    $exitCode = Artisan::call('config:clear');
    return '<h1>config:clear done</h1>';
});

Route::get('/optimize', function() {
    $exitCode = Artisan::call('optimize');
    return '<h1>oprimize done</h1>';
});

Route::get('/route-cache', function() {
    $exitCode = Artisan::call('route:cache');
    return '<h1>route:cache done</h1>';
});

Route::get('/route-clear', function() {
    $exitCode = Artisan::call('route:clear');
    return '<h1>route:clear done</h1>';
});

Route::get('/view-clear', function() {
    $exitCode = Artisan::call('view:clear');
    return '<h1>view:clear done</h1>';
});

Route::get('/config-cache', function() {
    $exitCode = Artisan::call('config:cache');
    return '<h1>config:cache done</h1>';
});

Route::get('/key-generate', function() {
    $exitCode = Artisan::call('key:generate');
    return '<h1>key:generate done</h1>';
});

Route::get('/storage-link', function() {
    Artisan::call('storage:link');
    return '<h1>storage:link done</h1>';
});

/*
|-------------------------------------------------
| Admin routes 
|-------------------------------------------------
|
*/

Auth::routes();


Route::get('/admin', 'Admin\AdminController@index');


Route::prefix('admin')->group(function () {

    Route::resources([
        'dogs' => 'Admin\DogController',
        'walks' => 'Admin\WalkController',
        'users' => 'Admin\UserController'
    ], ['except' => 'show']);

    Route::resource('profile', 'Admin\ProfileController')->only([
        'index', 'update'
    ]);
});


/*
|-------------------------------------------------
| Guest routes 
|-------------------------------------------------
|
*/

Route::get('/unreserved', 'Website\HomeController@unreserved');

Route::get('/confirm', 'Website\HomeController@confirm');

Route::get('/info', 'Website\HomeController@info');

Route::post('/walks', 'Website\WalkController@store');

Route::get('/admin', 'Admin\AdminController@index');

