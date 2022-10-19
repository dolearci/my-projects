<!--
|---------------------------------------
|   Autor: Daniel Havranek (xhavra18)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
    <h1>Upravit informace</h1>
@if(Session::has('custom_message'))
    <div class="alert alert-success" role="alert">
        {{ Session::get('custom_message') }}
    </div>
@endif
<form action="/admin/dogs/{{ $dog->id }}" method="POST" enctype="multipart/form-data">
    @csrf
    {{ method_field('PUT') }}

    <div class="form-group">
        <label for="name">Meno</label>
        <input type="text" name="name" class="form-control @error('name') is-invalid @enderror" value="@if($errors->any()){{ old('name') }}@else{{ $dog->name }}@endif">
        @error('name')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="breed">Plemeno</label>
        <input type="text" name="breed" class="form-control @error('breed') is-invalid @enderror" value="@if($errors->any()){{ old('breed') }}@else{{ $dog->breed }}@endif">
        @error('breed')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="age">Vek</label>
        <input type="number" name="age" class="form-control @error('age') is-invalid @enderror" value="@if($errors->any()){{ old('age') }}@else{{ $dog->age }}@endif">
        @error('age')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control @error('description') is-invalid @enderror" name="description" rows="5">@if($errors->any()){{ old('description') }}@else{{ $dog->description }}@endif</textarea>
        @error('description')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <div class="row">
            <div class="col-sm-6 col-lg-4 col-xl-3">
                <label class="d-block">Aktuální fotka </label>
                    
                <img class="img-fluid" src="{{ asset('/storage/dogs/' . $dog->id . '.jpg') }}"
                    alt="Fotka">
            </div>
            <div class="col-sm-6 col-lg-8 col-xl-9 pt-2">
                <label for="photo" class="d-block">Nová fotka</label>
                <input type="file" name="photo" accept="image/png, image/jpeg, image/jpg" class="mt-2">
                @error('photo')
                <div class="error">{{ $message }}</div>
                @enderror
            </div>
        </div>
    </div>

    <div>
    <button type="submit" class="btn btn-blue">Uložit</button>
    <a href="/admin/dogs" class="btn btn-white">Zpět</a>
    </div>
</form>
@endsection
