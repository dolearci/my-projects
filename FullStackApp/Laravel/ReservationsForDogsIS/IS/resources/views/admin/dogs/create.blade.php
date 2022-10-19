<!--
|---------------------------------------
|   Autor: Daniel Havranek (xhavra18)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
<h1>Přidat psika</h1>
<form action="/admin/dogs" method="POST" enctype="multipart/form-data">
    @csrf
    <div class="form-group">
        <label for="name">Meno</label>
        <input type="text" name="name" class="form-control @error('name') is-invalid @enderror" value="{{ old('name') }}">
        @error('name')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="breed">Plemeno</label>
        <input type="text" name="breed" class="form-control @error('breed') is-invalid @enderror" value="{{ old('breed') }}">
        @error('breed')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="age">Vek</label>
        <input type="number" name="age" class="form-control @error('age') is-invalid @enderror" value="{{ old('age') }}">
        @error('age')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control @error('description') is-invalid @enderror" name="description" rows="5">{{ old('description') }}</textarea>
        @error('description')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="photo" class="d-block">Fotka</label>
        <input type="file" name="photo" accept="image/png, image/jpeg, image/jpg">
        @error('photo')
        <div class="error">{{ $message }}</div>
        @enderror
    </div>
    
    <div>
        <button type="submit" class="btn btn-blue">Uložit</button>
        <a href="/admin/dogs" class="btn btn-white">Zpět</a>
    </div>
</form>
@endsection
