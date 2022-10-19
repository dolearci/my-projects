@extends('layouts.website')

@section('content')

<h1 class="text-center">Vytvoř aukci</h1>
{{-- id se vygeneruje samo
author je přihlašený uživatel
auctioneer se nastaví podle organizatora který schválí aukci
title je nazev aukce
tag select z kategorií
rule - otevřená, uzavřená aukce
description - popis
min_cost - začateční částka
max_cost - instant buy
start_at a end_at nastaví organizator
created_at - čas při odeslaní formulaře --}}
@php
$rules = array("otevřená","uzavřená");
$types = array("nabídková", "poptávková");
@endphp

<form action="/auctions/store/{{ Auth::user()->id }}" method="POST" id="auction-form">
    @csrf
    <div class="form-group">
        <label for="title">Název</label>
        <input type="text" name="title" class="form-control @error('title') is-invalid @enderror" value="{{ old('title') }}">
        @error('title')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="category">Kategorie</label>
        <select class="form-control" id="category" name="category">
            @foreach ($category_list as $category)
            <option value="{{ $category->id }}">{{ $category->title }}</option>
            @endforeach
        </select>
        @error('category')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control @error('description') is-invalid @enderror" name="description" rows="5">{{ old('description') }}</textarea>
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
        <input type="number" min="1" name="min_cost" class="form-control @error('min_cost') is-invalid @enderror" value="{{ old('min_cost') }}">
        @error('min_cost')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="max_cost">Maximální hodnota</label>
        <input type="number" min="1" name="max_cost" class="form-control @error('max_cost') is-invalid @enderror" value="{{ old('max_cost') }}">
        @error('max_cost')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    
    <button type="submit" class="btn btn-primary">Odeslat</button>
</form>

@endsection
