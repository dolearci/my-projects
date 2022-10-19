<!--
|---------------------------------------
|   Autor: Nina Štefeková (xstefe11)
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
<form action="/admin/walks/{{ $walk->id }}" method="POST">
    @csrf
    {{ method_field('PUT') }}

    <div class="form-group">
        <label for="name">Meno</label>
        <input type="text" name="name" class="form-control @error('name') is-invalid @enderror" value="@if($errors->any()){{ old('name') }}@else{{ $walk->name }}@endif">
        @error('name')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" name="email" class="form-control @error('email') is-invalid @enderror" value="@if($errors->any()){{ old('email') }}@else{{ $walk->email }}@endif">
        @error('email')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="address">Adresa bydliště</label>
        <input type="text" name="address" class="form-control @error('address') is-invalid @enderror" value="@if($errors->any()){{ old('address') }}@else{{ $walk->address }}@endif">
        @error('address')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="phone">Telefon</label>
        <input type="tel" name="phone" class="form-control @error('phone') is-invalid @enderror" value="@if($errors->any()){{ old('phone') }}@else{{ $walk->phone }}@endif">
        @error('phone')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="date_time">Termín</label>
        <input type="datetime-local" name="date_time" class="form-control @error('date_time') is-invalid @enderror" value="@if($errors->any()){{ old('date_time') }}@else{{ $walk->date_time }}@endif">
        @error('date_time')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="dog_id">Pes</label>
        <select class="form-control" name="dog_id">
            @foreach($dogs as $dog)
                <option value="{{ $dog->id }}" @if($walk->dog_id == $dog->id) selected @endif>{{ $dog->name }} ({{ $dog->breed }})</option>
            @endforeach
        </select>
    </div>

    <div>
    <button type="submit" class="btn btn-blue">Uložit</button>
    <a href="/admin/walks" class="btn btn-white">Zpět</a>
    </div>
</form>
@endsection
