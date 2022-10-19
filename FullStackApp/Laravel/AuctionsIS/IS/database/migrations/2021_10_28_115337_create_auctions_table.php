<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateAuctionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('auctions', function (Blueprint $table) {
            $table->id();
            $table->unsignedBigInteger('author_id');
            $table->foreign('author_id')->references('id')->on('users');
            $table->unsignedBigInteger('auctioneer_id')->nullable();
            $table->foreign('auctioneer_id')->references('id')->on('users');
            $table->string('title');
            $table->string('type');
            $table->string('rule');
            $table->text('description');
            $table->integer('min_cost');
            $table->integer('max_cost');
            $table->datetime('start_at')->nullable();
            $table->datetime('end_at')->nullable();
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
        
        Schema::table('auctions', function (Blueprint $table) {
            $table->dropForeign(['author_id']);
            $table->dropForeign(['auctioneer_id']);
        });
        Schema::dropIfExists('auctions');
    }
}
