
<!--
|---------------------------------------
|   Autor: Nina Štefeková (xstefe11)
|---------------------------------------
|
-->

@extends('layouts.website')

@section('content')

<h2>Volní psíkovia</h2>

<div class="row" id="cards">
	@foreach($dogs as $dog)
	<div class="col-12 col-sm-6 col-md-4 col-lg-3">
		<div class="card">
			<img class="card-img-top" src="{{ asset('storage/dogs/' . $dog->id . '.jpg') }}" alt="Card image" style="width:100%">
			<div class="card-body">
			  <h4 class="card-title">{{$dog->name}}</h4>
			  <p class="card-text"><b>Plemeno:</b> {{$dog->breed}}<br>
				<b>Vek:</b> {{$dog->age}}<br>
				{{$dog->description}}</p>
				<form action="/confirm" method="GET">
					<input type="hidden" name="date_time" value="{{$date_time}}"/>
					<input type="hidden" name="dog_id" value="{{$dog->id}}"/>
					<button type="submit" class="btn btn-custom">Vybrať</button>
				</form>
			</div>
		  </div>
	</div>
	@endforeach
</div>

@endsection
