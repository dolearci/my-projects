<?php

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

// auctions

Route::get('/', 'AuctionController@index');


Route::get('/auctions/new', 'AuctionController@new');
Route::get('/auctions/ending', 'AuctionController@ending');
Route::get('/auctions/upcoming', 'AuctionController@upcoming');


Route::get('/category/{category_id}-{category_slug}', 'AuctionController@category');


Route::middleware('auth')->group(function () {
    Route::get('/auctions/results', 'AuctionController@results');
    
    Route::get('/auctions/my-auctions', 'AuctionController@my_auctions');
    Route::get('/auctions/create', 'AuctionController@create');
    Route::post('/auctions/store/{user_id}', 'AuctionController@store');
    Route::get('/auctions/{auction_id}/edit', 'AuctionController@edit');
    Route::get('/auctions/{auction_id}/confirm', 'AuctionController@confirm');
    Route::put('/auctions/{auction_id}', 'AuctionController@update');
    Route::delete('/auctions/{auction_id}', 'AuctionController@destroy');
    Route::get('/auctions/{auction_id}/store_auctioneer/{auctioneer_id}', 'AuctionController@store_auctioneer');

    Route::get('/auction/{auction_id}/sign-up', 'AuctionController@sign_up');
    Route::get('/auction/{auction_id}/signed-users', 'AuctionController@signed_users');

    Route::get('/users/confirm/{auction_id}', 'UserController@confirm');
    Route::get('/users/unconfirm/{auction_id}', 'UserController@unconfirm');


    Route::get('/auction/{auction_id}/signed-users/{user_id}/confirm', 'UserController@confirm');
    Route::get('/auction/{auction_id}/signed-users/{user_id}/unconfirm', 'UserController@unconfirm');

    Route::post('/offers/{auction_id}/bid', 'OfferController@store');

    Route::post('/auction/{auction_id}/store-picture', 'PictureController@store');

    Route::delete('/auction/{auction_id}/pictures/{pictures_id}', 'PictureController@destroy');
});
Route::middleware(['auth','can:admin_or_auctioneer'])->group(function () {
    Route::get('/auctions/approved', 'AuctionController@approved');
    Route::put('/auctions/{auction_id}/approve', 'AuctionController@approve');
    Route::get('/auctions/unapproved', 'AuctionController@unapproved');
});

Route::get('/auctions/{auction_id}', 'AuctionController@show');

// users 

Route::middleware(['auth', 'can:admin'])->group(function () {
    Route::get('/users', 'UserController@index');
    Route::get('/users/{user_id}', 'UserController@show');
    Route::get('/users/{user_id}/edit', 'UserController@edit');
    Route::put('/users/{user_id}', 'UserController@update');
    Route::delete('/users/{user_id}', 'UserController@destroy');
});

// search
Auth::routes();

Route::post('/search', 'AuctionController@search_auction');
