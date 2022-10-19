@extends('layouts.website')

@section('content')
<h1>Výsledky</h1>
<table class="table">
    <thead>
        <tr>
            <th scope="col">Aukce</th>
            <th scope="col">Vítěz</th>
            <th scope="col">Vítězná částka</th>
            <th scope="col">Organizátor</th>
        </tr>
    </thead>
    <tbody>
        @foreach ($auction_list as $auction)
        <tr>
            <td>{{ $auction->title }}</td>
            <td>{{ $auction->winner()->name }}</td>
            <td>@if($auction->type == "poptávková") {{ $auction->top_bid() }} @else {{ $auction->lowest_bid() }} @endif</td>
            <td>{{ $auction->auctioneer->name }}</td>
        </tr>
        @endforeach
    </tbody>
</table>

{{-- <div class="pagination-inline">{{ $users->links() }}</div> --}}

@endsection
