<?php

use Illuminate\Support\Str;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Database\Seeder;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
            DB::table('users')->insert([
                'name' => 'Test Admin',
                'email' => 'admin@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'admin',  
            ]);

            DB::table('users')->insert([
                'name' => 'Test Auctioneer',
                'email' => 'auctioneer@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'auctioneer',  
            ]); 

            DB::table('users')->insert([
                'name' => 'Test Auctioneer 2',
                'email' => 'auctioneer2@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'auctioneer',  
            ]); 

            DB::table('users')->insert([
                'name' => 'Test User',
                'email' => 'user@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'user',  
            ]); 
            
            DB::table('users')->insert([
                'name' => 'Ing. Karel Novák',
                'email' => 'karelnovak@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'user',
            ]);
    
            DB::table('users')->insert([
                'name' => 'Martina Dovřecká',
                'email' => 'martina@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'user',
            ]);
    
            DB::table('users')->insert([
                'name' => 'Eva Zelená',
                'email' => 'eva@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'user',  
            ]);   

            DB::table('users')->insert([
                'name' => 'Jan Novák',
                'email' => 'novak.jan@gmail.com',
                'password' => Hash::make('password'),
                'role' => 'user',  
            ]);   
    }
}
