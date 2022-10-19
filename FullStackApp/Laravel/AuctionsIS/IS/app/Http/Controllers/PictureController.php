<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Picture;
use Illuminate\Support\Facades\File;
use Image;

class PictureController extends Controller
{

    public function store(Request $request, $auction_id)
    {   
        $image = $request->file('file');

        $image_name = time() . '_' . $image->getClientOriginalName();

        $image_upload = Image::make($image)->orientate()->resize(1000, null, function ($constraint) {
            $constraint->aspectRatio();
        });
        $image_upload->save('storage/auctions/' . $auction_id . '/' . $image_name, 60);

        $picture = new Picture();
        $picture->file_name = $image_name;
        $picture->auction_id = $auction_id;
        $picture->save();

        return redirect()->back();
    }


    public function destroy($auction_id, $picture_id)
    {
        $picture = Picture::findOrFail($picture_id);
        if(File::exists(storage_path('app/public/auctions/' . $auction_id . '/' . $picture->file_name))){
            File::delete(storage_path('app/public/auctions/' . $auction_id . '/' . $picture->file_name));
        }
        $picture->delete();

        return redirect()->back();
    }
}
