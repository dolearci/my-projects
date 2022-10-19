'use client';

import React from 'react';
import Link from 'next/link';

import {
	useGetMoviesByIds,
	useMovieListQuery,
	useMoviesInListQuery
} from '@/hooks/movie-list';
import MovieTile from '@/app/components/movie/movie-tile';
import Loading from '@/app/loading';

type PageProps = {
	params: {
		id: string;
	};
};

const Page: React.FC<PageProps> = ({ params }) => {
	const listId = Number.parseInt(params.id);
	const { data, isPending, isError, error } = useMovieListQuery(listId);
	const moviesIdsQuery = useMoviesInListQuery(listId);
	const moviesIds = moviesIdsQuery.data?.map(item => item.movieId);
	const moviesQuery = useGetMoviesByIds(moviesIds);
	console.log(moviesQuery.map(item => item.data));

	if (
		isPending ||
		moviesIdsQuery.isPending ||
		moviesQuery.some(item => item.isPending)
	) {
		return <Loading/>;
	}

	if (isError) {
		return (
			<div>
				An error occured during fetch, please try again. Error:
				{error.message}
			</div>
		);
	} else if (moviesIdsQuery.isError) {
		return (
			<div>
				An error occured during fetch, please try again. Error:
				{moviesIdsQuery.error.message}
			</div>
		);
	} else if (moviesQuery.some(item => item.isError)) {
		return <div>An error occured during fetch, please try again. Error:</div>;
	}

	return (
		<div className="min-h-screen p-6">
			<div className="mx-auto">
			<h1 className="text-mh-theme font-headings text-2xl font-bold mb-6">
				{data?.ListName?.toString()}
			</h1>
			{moviesIds?.length === 0 ? (
				<div>The list is empty.</div>
			) : (
				<div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-4">
					{moviesQuery.map(movie => (
						<Link key={movie.data.id} href={`/movie-detail/${movie.data.id}`}>
							<MovieTile
								imageUrl={movie.data.poster_path}
								title={movie.data.title}
								year={movie.data.release_date.split('-')[0]}
								rating={movie.data.vote_average.toPrecision(2)}
								description={movie.data.overview}
								genres={movie.data.genres.map(
									(item: { id: number; name: string }) => item.id
								)}
							/>
						</Link>
					))}
				</div>
			)}
					</div>
		</div>
	);
};

export default Page;
