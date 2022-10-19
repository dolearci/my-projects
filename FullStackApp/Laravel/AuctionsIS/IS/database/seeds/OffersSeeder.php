<?php

use Illuminate\Support\Str;
use Illuminate\Database\Seeder;
use App\Offer;

class OffersSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $Offer = Offer::create([
            'user_id' => 6,
            'auction_id' => 1,
            'bid' => 23400,
        ]);

        $Offer = Offer::create([
            'user_id' => 7,
            'auction_id' => 1,
            'bid' => 28000,
        ]);

        $Offer = Offer::create([
            'user_id' => 4,
            'auction_id' => 1,
            'bid' => 29000,
        ]);

        $Offer = Offer::create([
            'user_id' => 5,
            'auction_id' => 2,
            'bid' => 21200,
        ]);

        $Offer = Offer::create([
            'user_id' => 1,
            'auction_id' => 2,
            'bid' => 29000,
        ]);

        $Offer = Offer::create([
            'user_id' => 1,
            'auction_id' => 3,
            'bid' => 22400,
        ]);

        $Offer = Offer::create([
            'user_id' => 5,
            'auction_id' => 3,
            'bid' => 29000,
        ]);
    }
}
