@extends('layouts.website')

@section('content')

<div class="container">
    <div class="row">
    @foreach ($auction_list as $auction)
        <div class="col-xs-8 col-sm-6 col-lg-3">
            {{-- Chtěl jsem to dat do componentu ale neumim passovat jine data než string --}}
            <a href="/auctions/{{$auction->id}}">
                <div class="card">
                    @if($auction->thumbnail())
                    <img src="{{ asset('storage/auctions/' . $auction->id . '/' . $auction->thumbnail()->file_name) }}" class="card-img-top">
                    @else 
                    <img class="card-img-top" src="{{ asset('storage/auctions/default.png') }}" alt="Thumbnail">
                    @endif
                    <div class="card-body" style="height: 220px">
                        <h5 class="card-title">{{$auction->title}}</h5>
                        <p class="card-text">{{$auction->type}} | {{$auction->rule}}</p>


                        @if($auction->type == "poptávková")
                            @guest
                                <p class="card-text">@if($auction->rule == "uzavřená") {{$auction->min_cost }} @else {{$auction->top_bid() }} @endif Kč</p>
                            @else
                                @if(Auth::user()->id == $auction->auctioneer_id || Auth::user()->id == $auction->author_id)
                                    <p class="card-text">{{$auction->top_bid() }} Kč</p>
                                @else 
                                    <p class="card-text">@if($auction->rule == "uzavřená") {{$auction->min_cost }} @else {{$auction->top_bid() }} @endif Kč</p>
                                @endif
                            @endguest
                        @else 
                            @guest
                                <p class="card-text">@if($auction->rule == "uzavřená") {{$auction->max_cost }} @else {{$auction->lowest_bid() }} @endif Kč</p>
                            @else
                                @if(Auth::user()->id == $auction->auctioneer_id || Auth::user()->id == $auction->author_id)
                                    <p class="card-text">{{$auction->lowest_bid() }} Kč</p>
                                @else 
                                    <p class="card-text">@if($auction->rule == "uzavřená") {{$auction->max_cost }} @else {{$auction->lowest_bid() }} @endif Kč</p>
                                @endif
                            @endguest
                        @endif
                        @if($auction->running())
                        <p>Právě probíhá</p>
                        @else
                        <p>@if($auction->auctioneer_id)Začíná {{$auction->start_at->format('d.m. Y H:i:s')}}@endif</p>
                        @endif
                    </div>
                </div>
            </a>
        </div>
    @endforeach
    </div>
</div>
    
@endsection
        
        

