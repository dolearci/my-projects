<?php

/*
|-------------------------------------------------
| Autor: Nina Štefeková (xstefe11)
|-------------------------------------------------
|
*/

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateWalksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('walks', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('dog_id');
            $table->foreign('dog_id')->references('id')->on('dogs');
            $table->datetime('date_time');
            $table->string('name');
            $table->string('email');
            $table->string('phone');
            $table->string('address');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('walks', function (Blueprint $table) {
            $table->dropForeign(['dog_id']);
        });
        Schema::dropIfExists('walks');
    }
}
