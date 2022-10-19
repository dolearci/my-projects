'use client';

import { useSession } from 'next-auth/react';

import MovieList from '@/app/components/movie-list/movie-list';
import { useAllListsQuery } from '@/hooks/movie-list';

import Loading from './loading';

const Page = () => {
	const session = useSession();
	const userId = session?.data?.user?.id;
	const { data, isPending, isError, error } = useAllListsQuery(userId);

	if (isPending) {
		return (
			<div className="fixed top-20 left-0 w-full h-1">
				<div className="h-full bg-blue-500 animate-loading" />
			</div>
		);
	}

	return (
		<div className="min-h-screen p-6">
			<div className="mx-auto">
				<h1 className="text-mh-theme font-headings text-2xl font-bold mb-6">
					Movie lists
				</h1>
				{isPending ? (
					<Loading />
				) : isError ? (
					<div>
						An error occured during fetch, please try again. Error:{' '}
						{error.message}
					</div>
				) : (
					<MovieList items={data} deletable={false} maxGridCols />
				)}
			</div>
		</div>
	);
};
export default Page;
