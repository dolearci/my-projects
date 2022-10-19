<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class CategorySeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('categories')->insert([
            'title' => 'Dům a zahrada',
        ]);
        DB::table('categories')->insert([
            'title' => 'Sběratelství',
        ]);
        DB::table('categories')->insert([
            'title' => 'Elektro',
        ]);
        DB::table('categories')->insert([
            'title' => 'Oblečení',
        ]);
        DB::table('categories')->insert([
            'title' => 'Auto-moto',
        ]);
        DB::table('categories')->insert([
            'title' => 'Zábava',
        ]);
        DB::table('categories')->insert([
            'title' => 'Služby',
        ]);
        DB::table('categories')->insert([
            'title' => 'Ostatní',
        ]);
    }
}
