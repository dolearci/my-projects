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

<form action="/auctions/{{ $auction->id }}" method="POST" id="auction-form">
    @csrf
    {{ method_field('PUT') }}
    <div class="form-group">
        <label for="title">Název</label>
        <input type="text" name="title" class="form-control @error('name') is-invalid @enderror" value="{{ $auction->title }}">
        @error('title')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="category">Kategorie</label>
        <select class="form-control" id="category" name="category">
            @foreach ($category_list as $category)
            <option value="{{ $category->id }}" @if($auction->category_id == $category->id) selected @endif>{{ $category->title }}</option>
            @endforeach
        </select>
        @error('category')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control @error('description') is-invalid @enderror" name="description" rows="5">{{ $auction->description }}</textarea>
        @error('condescriptiontent')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="type">Typ aukce</label>
        <select class="form-control" id="type" name="type">
            @foreach ($types as $type)
                <option value="{{ $type }}">{{ $type }}</option>
            @endforeach
        </select>
        @error('type')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="rule">Pravidlo</label>
        <select class="form-control" id="rule" name="rule">
            @foreach ($rules as $rule)
                <option value="{{ $rule }}">{{ $rule }}</option>
            @endforeach
        </select>
        @error('rule')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="min_cost">Minimální hodnota</label>
        <input type="number" min="1" name="min_cost" class="form-control @error('min_cost') is-invalid @enderror" value="{{ $auction->min_cost }}">
        @error('min_cost')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="max_cost">Maximální hodnota</label>
        <input type="number" min="1" name="max_cost" class="form-control @error('max_cost') is-invalid @enderror" value="{{ $auction->max_cost }}">
        @error('max_cost')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <button type="submit" class="btn btn-primary">Odeslat</button>
    
</form>
<form action="/auctions/{{$auction->id}}" method="POST">
    @csrf
    {{ method_field('DELETE') }}
    <button type="submit" class="btn">Smazat aukci</button>
</form>

<form method="post" action="{{url('auction/' . $auction->id . '/store-picture')}}" enctype="multipart/form-data" 
    class="dropzone" id="dropzone">
@csrf
<div class="dz-message">Pro nahrání fotografií zde klikněte nebo přetáhněte soubory</div>
</form> 

<div class="row">
    @foreach($pictures as $picture)
    <div class="col-md-3">
        <img class="img-fluid" src="{{ asset('storage/auctions/' . $auction->id . '/' . $picture->file_name) }}">
        <form action="/auction/{{$auction->id}}/pictures/{{ $picture->id }}" method="POST">
            @csrf
            {{ method_field('DELETE') }}
            <button type="submit" class="btn">Smazat</button>
        </form>
    </div>
    @endforeach
</div>

@endsection