@extends('layouts.website')

@section('content')
<h1>Uživatelé</h1>
@if(Session::has('custom_message'))
    <div class="alert alert-success" role="alert">
        {{ Session::get('custom_message') }}
    </div>
@endif
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Jméno</th>
            <th scope="col">Status</th>
            <th scope="col">Role</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        @foreach ($users as $user)
        <tr>
            <td>{{ $user->id }}</td>
            <td><a href="/users/{{ $user->id }}">{{ $user->name }}</a></td>
            <td>{{ $user->pivot->status }}</td>
            <td>{{ $user->role }}</td>
            <td class="text-right">
                <div class="btn-group" role="group">
                    <a href="/auction/{{$auction->id}}/signed-users/{{$user->id}}/confirm" class="btn btn-primary">Schválit</a>
                    <a href="/auction/{{$auction->id}}/signed-users/{{$user->id}}/unconfirm" class="btn btn-secondary">Neschválit</a>
                </div>
            </td>
        </tr>
        @endforeach
    </tbody>
</table>

{{-- <div class="pagination-inline">{{ $users->links() }}</div> --}}

@endsection
