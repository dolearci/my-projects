<?php

/*
|-------------------------------------------------
| Autor: Nina Štefeková (xstefe11)
|-------------------------------------------------
|
*/

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class DogSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('dogs')->insert([
            'name' => 'Alfonz',
            'breed' => 'Retrívr',
            'age' => 2,
            'description' => 'Klidný pejsek, má rád lidi.',
        ]);

        DB::table('dogs')->insert([
            'name' => 'Ferko',
            'breed' => 'Bígl',
            'age' => 4,
            'description' => 'Klidný pejsek, má rád lidi.',
        ]);

        DB::table('dogs')->insert([
            'name' => 'Alfred',
            'breed' => 'Yorkshire',
            'age' => 3,
            'description' => 'Klidný pejsek, má rád lidi.',
        ]);
    }
}
