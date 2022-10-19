import React from 'react';
import { type Row } from '@libsql/client';
import { type Session } from 'next-auth';

import {
	getMovieById,
	getMovieCast,
	getSimilarMoviesForId
} from '@/server/data/client/tmdb-client';
import { type MovieDetailDto } from '@/server/data/dto/movie-detail-dto';
import { type MovieDto } from '@/server/data/dto/movie-dto';
import { getAllMovieLists } from '@/server/data/client/db-client';
import { getServerAuthSession } from '@/server/auth';
import MovieInfo from '@/app/components/movie/movie-info';
import { type CreditsDtoArray } from '@/server/data/dto/credits-dto-array';

type PageProps = {
	params: {
		id: number;
	};
};

type ExtendedUser = {
	id?: number;
} & {
	name?: string | null | undefined;
	email?: string | null | undefined;
	image?: string | null | undefined;
};

type ExtendedSession = {
	user?: ExtendedUser;
} & Session;

const Page: React.FC<PageProps> = async ({ params }) => {
	const movie: MovieDetailDto = await getMovieById(params.id);
	const relatedMovies: MovieDto[] = await getSimilarMoviesForId(params.id, 4);
	const credits: CreditsDtoArray[] = await getMovieCast(params.id);

	const session: ExtendedSession | null = await getServerAuthSession();
	let rows: Row[] = [];

	if (session?.user?.id !== undefined) {
		const creatorId = session?.user?.id;
		rows = await getAllMovieLists(creatorId);
	}

	return (
		<MovieInfo
			movie={movie}
			relatedMovies={relatedMovies}
			credits={credits}
			session={session}
			rows={rows}
		/>
	);
};

export default Page;
