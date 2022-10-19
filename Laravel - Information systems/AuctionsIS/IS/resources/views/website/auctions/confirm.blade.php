@extends('layouts.website')

@section('content')

@php
$rules = array("otevřená","uzavřená");
$types = array("nabídková", "poptávková");
@endphp


@if(Session::has('custom_message'))
    <div class="alert alert-success" role="alert">
        {{ Session::get('custom_message') }}
    </div>
@endif

<h1 class="text-center">Schválit aukci</h1>
<form action="/auctions/{{$auction->id}}/approve" method="POST" id="auction-form">
    @csrf
    {{ method_field('PUT') }}
    <div class="form-group">
        <label for="title">Název</label>
        <input type="text" name="title" class="form-control" disabled value="{{$auction->title}}">
    </div>
    <div class="form-group">
        <label for="category">Kategorie</label>
        <input type="text" name="category" class="form-control" disabled value="{{$auction->category->title}}">
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control" name="description" disabled>{{$auction->description}}</textarea>
    </div>

    <div class="form-group">
        <label for="type">Typ aukce</label>
        <input type="text" name="type" class="form-control" disabled value="{{$auction->type}}">
    </div>

    <div class="form-group">
        <label for="rule">Pravidlo</label>
        <input type="text" name="rule" class="form-control" disabled value="{{$auction->rule}}">
    </div>

    <div class="form-group">
        <label for="min_cost">Počáteční nabídka</label>
        <input type="text" name="min_cost" class="form-control" disabled value="{{$auction->min_cost}}">
    </div>

    <div class="form-group">
        <label for="max_cost">Okamžitý nákup</label>
        <input type="text" name="max_cost" class="form-control" disabled value="{{$auction->max_cost}}">
    </div>

    <div class="form-group">
        <label for="start_at">Začátek aukce</label>
        <input type="datetime-local" name="start_at" class="form-control @error('start_at') is-invalid @enderror" value="{{ str_replace(' ', 'T', old('start_at')) }}">
        @error('start_at')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="end_at">Konec aukce</label>
        <input type="datetime-local" name="end_at" class="form-control @error('end_at') is-invalid @enderror" value="{{ str_replace(' ', 'T', old('end_at')) }}">
        @error('end_at')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <button type="submit" class="btn btn-primary">Schválit</button>
</form>
<form action="/auctions/{{ $auction->id }}" method="POST">
    @csrf
    {{ method_field('DELETE') }}
    <button type="submit" class="btn"
        onclick="return confirm('Opravdu chcete smazat tuto aukci?')">Smazat</button>
</form>

@endsection