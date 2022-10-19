'use client';

import { useSession } from 'next-auth/react';

import MovieList from '@/app/components/movie-list/movie-list';
import {
	useCreatedListsQuery,
	useFavouriteListsQuery
} from '@/hooks/movie-list';
import { MovieListCreate } from '@/app/components/movie-list/create-movie-list';

const MovieListManagement = () => {
	const session = useSession();
	const userId = session?.data?.user?.id;
	const favourites = useFavouriteListsQuery(userId);
	const created = useCreatedListsQuery(userId);

	return (
		<div className="min-h-screen p-6">
			<div className="mx-auto">
				<h1 className="text-mh-theme font-headings text-2xl font-bold mb-6">
					My movie lists
				</h1>
				<div className="flex-row xl:flex  mt-10">
					<div className="flex flex-col mr-10 w-full xl:w-1/2 items-center">
						<h2 className="text-xl text-black shadow-lg py-4 border-[1px] rounded-lg border-gray-100 font-headings w-full text-center mb-2">
							Favourite lists
						</h2>
						{favourites.isPending ? (
							<div className="loading loading-dots loading-lg" />
						) : favourites.isError ? (
							<div>
								An error occured during fetch, please try again. Error:{' '}
								{favourites.error.message}
							</div>
						) : favourites.data.length > 0 ? (
							<MovieList items={favourites.data} deletable={false} />
						) : (
							<div className="text-center">Favorites are empty.</div>
						)}
					</div>
					<div className="flex flex-col ml-1 w-full xl:w-1/2 items-center">

						<h2 className="text-xl text-black shadow-lg py-4 border-[1px] mt-4 lg:mt-0 rounded-lg  border-gray-100 font-headings w-full text-center mb-2">
							Created lists
						</h2>
						<MovieListCreate />
						{created.isPending ? (
							<div className="loading loading-dots loading-lg" />
						) : created.isError ? (
							<div>
								An error occured during fetch, please try again. Error:{' '}
								{created.error.message}
							</div>
						) : created.data.length > 0 ? (
							<MovieList items={created.data} deletable />
						) : (
							<div className="text-center">No lists created yet.</div>
						)}
					</div>
				</div>
			</div>
		</div>
	);
};

export default MovieListManagement;
