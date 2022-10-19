'use client';
import React, { useState } from 'react';
import Link from 'next/link';
import {
	type GetServerSideProps,
	type InferGetServerSidePropsType
} from 'next';

import MovieTile from '@/app/components/movie/movie-tile';
import { type MovieDto } from '@/server/data/dto/movie-dto';
import PaginationControls from '@/app/components/pagination/pagination-controls';
import MovieFilters from '@/app/components/movie/movie-filters';

type Filter = {
	page: number;
	perPage: number;
	year: number | null;
	genre: string;
};

type PageProps = {
	searchParams: Filter;
};

const getMoviesServerSideProps: GetServerSideProps = async context => {
	const searchParams: Filter = {
		page: parseInt(context.query.page as string) || 1,
		perPage: parseInt(context.query.per_page as string) || 12,
		year: context.query.year ? parseInt(context.query.year as string) : null,
		genre: (context.query.genre as string) || ''
	};

	return {
		props: { searchParams }
	};
};

const Page: React.FC<PageProps> = ({
	searchParams
}: InferGetServerSidePropsType<typeof getMoviesServerSideProps>) => {
	const [movies, setMovies] = useState<MovieDto[]>([]);

	return (
		<div className="min-h-screen p-6">
			<div className="mx-auto">
				<h1 className="text-mh-theme font-headings text-2xl font-bold mb-6">
					Best Of
				</h1>
				<MovieFilters
					initialFilters={searchParams}
					onMoviesFetched={setMovies}
				/>
				<div className="animation-fadeIn grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-4">
					{movies.map((movie, index) => (
						<Link key={index} href={`/movie-detail/${movie.id}`}>
							<MovieTile
								imageUrl={movie.poster_path}
								title={movie.title}
								year={movie.release_date.split('-')[0]}
								rating={movie.vote_average.toPrecision(2)}
								description={movie.overview}
								genres={movie.genre_ids}
							/>
						</Link>
					))}
				</div>
				<PaginationControls baseUrl="best-of" />
			</div>
		</div>
	);
};

export default Page;
