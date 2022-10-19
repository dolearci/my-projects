<!--
|---------------------------------------
|   Autor: Daniel Havranek (xhavra18)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
<h1>Psíkovia</h1>
@if(Session::has('custom_message'))
    <div class="alert alert-success" role="alert">
        {{ Session::get('custom_message') }}
    </div>
@endif
@if($dogs->total() != 0)
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Meno</th>
            <th scope="col">Plemeno</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        @foreach ($dogs as $dog)
        <tr>
            <td>{{ $dog->id }}</td>
            <td>{{ $dog->name }}</td>
            <td>{{ $dog->breed }}</td>
            <td class="text-right">
                <div class="btn-group" role="group">
                    <a href="/admin/dogs/{{ $dog->id }}/edit" 
                            class="btn btn-white float-left mr-2"><i class="fas fa-pen"></i></a>
                    <form action="/admin/dogs/{{ $dog->id }}" method="POST">
                        @csrf
                        {{ method_field('DELETE') }}
                        <button type="submit" class="btn btn-white"
                            onclick="return confirm('Opravdu chcete odstranit tohoto psa?')"><i
                                class="fas fa-trash"></i></button>
                    </form>
                </div>
            </td>
        </tr>
        @endforeach
    </tbody>
</table>
@endif

<div class="pagination-inline">{{ $dogs->links() }}</div>
<a href="/admin/dogs/create" class="btn btn-blue btn-inline">Přidat</a>

@endsection
