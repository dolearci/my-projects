import Image from 'next/image';
import Link from 'next/link';
import React from 'react';
import { type Row } from '@libsql/client';
import { type Session } from 'next-auth';

import MoodButton from '@/app/components/movie/mood-button';
import HeartButton from '@/app/components/movie/heart-button';
import type { CrewDto } from '@/server/data/dto/crew-dto';
import type { CastDto } from '@/server/data/dto/cast-dto';
import MovieTile from '@/app/components/movie/movie-tile';
import { type CreditsDtoArray } from '@/server/data/dto/credits-dto-array';
import { type MovieDto } from '@/server/data/dto/movie-dto';
import { type MovieDetailDto } from '@/server/data/dto/movie-detail-dto';

type MovieInfoProps = {
	movie: MovieDetailDto;
	relatedMovies: MovieDto[];
	credits: CreditsDtoArray[];
	session: Session | null;
	rows: Row[];
};

const MovieInfo = ({
	movie,
	relatedMovies,
	credits,
	session,
	rows
}: MovieInfoProps) => {
	const formatDuration = (minutes: number) => {
		const hours = Math.floor(minutes / 60);
		const remainingMinutes = minutes % 60;
		return `${hours} hr ${remainingMinutes} min`;
	};

	const directors = credits.crew
		.filter((c: CrewDto) => c.job === 'Director')
		.slice(0, 3);

	const writers = credits.crew
		.filter((c: CrewDto) => c.job === 'Novel' || c.job === 'Writer')
		.slice(0, 3);

	const orderedCast = credits.cast.sort(
		(a: CastDto, b: CastDto) => a.order - b.order
	);
	const actors = orderedCast
		.filter((c: CastDto) => c.known_for_department === 'Acting')
		.slice(0, 4);
	return (
		<div className="p-4 md:p-10 min-h-screen text-mh-theme top-3 text-left">
			<div className="mx-auto  text-lg">
				<div className="grid grid-cols-1 xl:grid-cols-2 gap-8 w-full">
					<div className="md:col-span-1">
						<Image
							width={600}
							height={700}
							src={`https://image.tmdb.org/t/p/original${movie.poster_path}`}
							alt="Movie Poster"
							className="rounded-lg shadow-lg"
						/>
					</div>
					<div className="md:col-span-1">
						<h1 className="text-3xl md:text-4xl font-bold font-headings mb-1">
							{movie.title}
						</h1>
						<div className="w-full font-headings flex text-xl md:text-xl space-x-10 text-left pt-1 md:pl-2">
							<p>{movie.release_date.split('-')[0]}</p>
							<p>{formatDuration(movie.runtime)}</p>
							<p>{movie.vote_average.toPrecision(3)}/10</p>
						</div>
						<div className="flex items-center gap-2 my-4">
							{movie.genres.map(genre => (
								<MoodButton
									key={genre.id}
									backgroundColor="white"
									label={genre.name}
									className="cursor-default"
								/>
							))}
							{session !== null && (
								<HeartButton
									movieLists={rows}
									movieId={movie.id}
									movieTitle={movie.title}
								/>
							)}
						</div>
						<p className="font-content text-md">{movie.overview}</p>
						<div className="w-1/2">
							<div className="mt-10 grid  md:grid-cols-2">
								<h2 className="flex items-center text-xl font-bold font-headings">
									Directed by
								</h2>
								<p className="font-content flex items-center">
									{directors.map((director: CrewDto) => director.name)}
								</p>
							</div>
							<div className="mt-4 grid  md:grid-cols-2">
								<h2 className="text-xl font-bold font-headings">Written by</h2>
								<div className="font-content">
									{writers.map((writer: CrewDto) => (
										<p className="pt-[6px]" key={writer.id}>
											{writer.name}
										</p>
									))}
								</div>
							</div>
							<div className="mt-4 grid  md:grid-cols-2">
								<h2 className="text-xl font-bold font-headings">Cast</h2>
								<div className="font-content">
									{actors.map((actor: CastDto) => (
										<p className="pt-[6px]" key={actor.order}>
											{actor.name}
										</p>
									))}
								</div>
							</div>
						</div>

						<div className="mt-10 ">
							<h2 className="text-xl font-bold font-headings col-span-1 ">
								Production Companies
							</h2>
							<div className="col-span-1 pl-1">
								{movie.production_companies
									.sort(
										(a, b) => (b.logo_path ? 1 : -1) - (a.logo_path ? 1 : -1)
									)
									.map(company => (
										<div key={company.id} className="flex items-center mt-4">
											{company.logo_path !== null && (
												<Image
													src={`https://image.tmdb.org/t/p/original${company.logo_path}`}
													alt={company.name}
													width={150}
													height={100}
													className="mr-5 "
												/>
											)}
											<p
												className={`${company.logo_path ? company.logo_path : 'my-2 font-content text-xl'} font-content`}
											>
												{company.name}
											</p>
										</div>
									))}
							</div>
						</div>
					</div>
				</div>
			</div>
			<div className="w-full">
				<h2 className="text-2xl font-bold font-headings mt-10 mb-5">
					More like this
				</h2>
				<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-4">
					{relatedMovies.map((movie, index) => (
						<Link key={index} href={`/movie-detail/${movie.id}`}>
							<MovieTile
								imageUrl={movie.poster_path}
								key={index}
								title={movie.title}
								year={movie.release_date.split('-')[0]}
								rating={movie.vote_average.toPrecision(2)}
								description={movie.overview}
								genres={movie.genre_ids}
							/>
						</Link>
					))}
				</div>
			</div>
		</div>
	);
};

export default MovieInfo;
