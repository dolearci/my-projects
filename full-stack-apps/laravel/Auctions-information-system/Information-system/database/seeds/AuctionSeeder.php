<?php

use Illuminate\Support\Str;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\Storage;
use App\User;
use App\Auction;

class AuctionSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // id 1 = admin, id 2 a id 3 = auctioneer
        $users = User::get();

        $auction = Auction::create([
            'author_id' => 1,
            'title' => 'XBox One',
            'auctioneer_id' => 2,
            'category_id' => 6,
            'type' => 'poptávková',
            'rule' => 'otevřená',
            'description' => 'Zachovalý',
            'min_cost' => 2000,
            'max_cost' => 30000,
            'start_at' => now()->add(new DateInterval('P6D')),
            'end_at' => now()->add(new DateInterval('P12D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 6,
            'title' => 'Starožitné hodinky',
            'auctioneer_id' => 2,
            'category_id' => 2,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'Hodně staré hodinky',
            'min_cost' => 2000,
            'max_cost' => 30000,
            'start_at' => now() ,
            'end_at' => now()->add(new DateInterval('P2D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 2,
            'title' => 'Apple watch',
            'auctioneer_id' => 3,
            'category_id' => 3,
            'type' => 'nabídková',
            'rule' => 'otevřená',
            'description' => 'Nové ',
            'min_cost' => 2000,
            'max_cost' => 30000,
            'start_at' => now(),
            'end_at' => now()->add(new DateInterval('P13D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 5,
            'title' => 'Škoda Felicia GLX 1.6',
            'auctioneer_id' => 3,
            'category_id' => 5,
            'type' => 'nabídková',
            'rule' => 'uzavřená',
            'description' => 'Velice zachovalé auto. Shrnovací střecha je originální. Tento model byl určen jen pro export...',
            'min_cost' => 5000,
            'max_cost' => 1000000,
            'start_at' => now()->add(new DateInterval('P1D')),
            'end_at' => now()->add(new DateInterval('P5D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 1,
            'title' => 'Škoda Superb',
            'auctioneer_id' => 2,
            'category_id' => 5,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'Ide ako diabol',
            'min_cost' => 2000,
            'max_cost' => 200000,
            'start_at' => now() ,
            'end_at' => now()->add(new DateInterval('P2D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 4,
            'title' => 'Škoda Octavia RS',
            'auctioneer_id' => 2,
            'category_id' => 5,
            'type' => 'poptávková',
            'rule' => 'otevřená',
            'description' => '140 kw zachovale',
            'min_cost' => 6000,
            'max_cost' => 90000,
            'start_at' => now()->add(new DateInterval('P5D')),
            'end_at' => now()->add(new DateInterval('P6D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 2,
            'title' => 'Škoda Forman',
            'category_id' => 5,
            'type' => 'nabídková',
            'rule' => 'uzavřená',
            'description' => 'Raketa',
            'min_cost' => 200000,
            'max_cost' => 20000000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 3,
            'title' => 'Škoda Superb 2019',
            'category_id' => 5,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'Pohoda',
            'min_cost' => 300000,
            'max_cost' => 30000000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 5,
            'title' => 'Dom Brno',
            'category_id' => 1,
            'type' => 'nabídková',
            'rule' => 'otevřená',
            'description' => 'Velmi dlhy description lebo Brno je top a tak chapete asddasdasdasdasdasddddddddddddddddddddddddddddddddddddddd',
            'min_cost' => 1000,
            'max_cost' => 90000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 6,
            'title' => 'Dom Bardejov',
            'auctioneer_id' => 2,
            'category_id' => 1,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'Luxus',
            'min_cost' => 3000,
            'max_cost' => 80000,
            'start_at' => now(),
            'end_at' => now()->add(new DateInterval('P2D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 7,
            'title' => 'Dom Opava',
            'category_id' => 1,
            'type' => 'poptávková',
            'rule' => 'otevřená',
            'description' => 'Pekny dom v centre nicoho',
            'min_cost' => 2000,
            'max_cost' => 30000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 4,
            'title' => 'Stare hodziny',
            'auctioneer_id' => 2,
            'category_id' => 2,
            'type' => 'nabídková',
            'rule' => 'otevřená',
            'description' => 'idu furt jak nove',
            'min_cost' => 1000,
            'max_cost' => 90000,
            'start_at' => now(),
            'end_at' => now()->add(new DateInterval('P8D')),
        ]);

        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }

        $auction = Auction::create([
            'author_id' => 5,
            'title' => 'Opasok rok 1800',
            'category_id' => 2,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'najlepsi opasok aky bol vyrobeny',
            'min_cost' => 3000,
            'max_cost' => 80000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 6,
            'title' => 'Podkova pre stastie',
            'auctioneer_id' => 3,
            'category_id' => 2,
            'type' => 'poptávková',
            'rule' => 'otevřená',
            'description' => 'podkova z kona',
            'min_cost' => 2000,
            'max_cost' => 30000,
            'start_at' => now()->add(new DateInterval('P1D')),
            'end_at' => now()->add(new DateInterval('P8D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 7,
            'title' => 'Apple MAC 2020',
            'category_id' => 3,
            'type' => 'nabídková',
            'rule' => 'otevřená',
            'description' => 'novinka predavam bo nemam money',
            'min_cost' => 1000,
            'max_cost' => 90000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 4,
            'title' => 'Apple Iphone XR',
            'category_id' => 3,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'kus skrabnuty ide dat simka',
            'min_cost' => 3000,
            'max_cost' => 80000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 5,
            'title' => 'Samsung Telka',
            'auctioneer_id' => 2,
            'category_id' => 3,
            'type' => 'poptávková',
            'rule' => 'otevřená',
            'description' => '110 palcov na celu stenu',
            'min_cost' => 2000,
            'max_cost' => 30000,
            'start_at' => now(),
            'end_at' => now()->add(new DateInterval('P7D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


        foreach($users as $user) {
            if($user->id != $auction->author_id && $user->id != $auction->auctioneer_id) {
                if($user->id % 2 == 0) {
                    $auction->users()->attach($user, array('status' => 'registered'));
                } else {
                    $auction->users()->attach($user);
                }
            }
        }
        
        $auction = Auction::create([
            'author_id' => 6,
            'title' => 'Balenciaga capica',
            'category_id' => 4,
            'type' => 'nabídková',
            'rule' => 'otevřená',
            'description' => 'topka idu cajky za tym',
            'min_cost' => 1000,
            'max_cost' => 90000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 7,
            'title' => 'CK nohavice',
            'category_id' => 4,
            'type' => 'poptávková',
            'rule' => 'uzavřená',
            'description' => 'kus roztrhane',
            'min_cost' => 3000,
            'max_cost' => 80000,
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);

        $auction = Auction::create([
            'author_id' => 4,
            'title' => 'tielko VIN DIESEL',
            'auctioneer_id' => 2,
            'category_id' => 4,
            'type' => 'poptávková',
            'rule' => 'otevřená',
            'description' => 'uz len auto ku nemu a ides',
            'min_cost' => 2000,
            'max_cost' => 30000,
            'start_at' => now(),
            'end_at' => now()->add(new DateInterval('P5D')),
        ]);
        Storage::makeDirectory('public/auctions/' . $auction->id, $mode = 0777, true, true);


    }
}
