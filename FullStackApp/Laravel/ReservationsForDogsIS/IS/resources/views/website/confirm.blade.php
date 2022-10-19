
<!--
|---------------------------------------
|   Autor: Daniel Olearčin (xolear00)
|---------------------------------------
|
-->

@extends('layouts.website')

@section('content')

<h2>Ešte niečo o vás</h2>
<form action="/walks" method="POST">
    @csrf
    <input type="hidden" name="date_time" value="{{$date_time}}" />
    <input type="hidden" name="dog_id" value="{{$dog_id}}" />
    <div class="form-group">
        <label for="name">Meno</label>
        <input type="text" class="form-control" name="name">
        @error('name')
        <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" class="form-control" name="email">
        @error('email')
        <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="address">Adresa bydliska</label>
        <input type="text" class="form-control" name="address">
        @error('address')
        <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="phone">Telefón</label>
        <input type="tel" class="form-control" name="phone">
        @error('phone')
        <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <button type="submit" class="btn btn-custom">Rezervovať</button>
</form>

@endsection
