import {
	useMutation,
	useQueries,
	useQuery,
	useQueryClient
} from '@tanstack/react-query';
import { toast } from 'react-toastify';
import { type Row } from '@libsql/client';

import { type MovieId, type MovieListItemType } from '@/types/movie-list';
import {
	createFavoriteList,
	createMovieList,
	deleteFavoriteListByUserAndList,
	deleteMovieList,
	getFavoriteListsByUser,
	getMovieList,
	getMovieListsByCreator,
	getMovieListsWithFavoritesByUser,
	getMoviesInList
} from '@/server/data/client/db-client';
import { getMovieById } from '@/server/data/client/tmdb-client';

const movieList = async (listId: number) => {
	const data = await getMovieList(listId);

	if (data) {
		return data;
	} else {
		throw new Error(`Failed to fetch movie list ${listId}.`);
	}
};

export const useMovieListQuery = (listId: number) =>
	useQuery({
		queryKey: ['movielist', listId],
		queryFn: () => movieList(listId)
	});

const movies = async (listId: number) => {
	const data = await getMoviesInList(listId);

	if (data) {
		return data;
	} else {
		throw new Error(`Failed to fetch movies in a list ${listId}.`);
	}
};

export const useMoviesQuery = (listId: number) =>
	useQuery({
		queryKey: ['movies', listId],
		queryFn: () => movies(listId)
	});

const favouriteLists = async (usersId: string) => {
	const favourites = await getFavoriteListsByUser(usersId);

	if (favourites) {
		const data = favourites.map(item => ({
			name: item.ListName,
			authorId: item.CreatorId,
			listId: item.ListId,
			listType: 'favourite'
		}));

		return data as MovieListItemType[];
	} else {
		throw new Error('Failed to fetch lists.');
	}
};

export const useFavouriteListsQuery = (userId: string) =>
	useQuery({
		queryKey: ['lists', userId, 'favourites'],
		queryFn: () => favouriteLists(userId)
	});

const createdLists = async (userId: string) => {
	const created = await getMovieListsByCreator(userId);

	if (created) {
		const data = created.map(item => ({
			name: item.ListName,
			authorId: item.CreatorId,
			listId: item.ListId,
			listType: 'author'
		}));

		return data as MovieListItemType[];
	} else {
		throw new Error('Failed to fetch lists.');
	}
};

export const useCreatedListsQuery = (userId: string) =>
	useQuery({
		queryKey: ['created', userId],
		queryFn: () => createdLists(userId)
	});

const allLists = async (userId: string) => {
	const allLists = await getMovieListsWithFavoritesByUser(userId);

	const getListType = (item: Row) => {
		if (item.CreatorId?.toString() === userId) {
			return 'author';
		} else if (item.FavoriteId !== null) {
			return 'favourite';
		}
		return 'other';
	};

	const data = allLists.map(item => ({
		name: item.ListName,
		authorId: item.CreatorId,
		listId: item.ListId,
		listType: getListType(item)
	}));
	return data as MovieListItemType[];
};

export const useAllListsQuery = (userId: string) =>
	useQuery({
		queryKey: ['lists', userId],
		queryFn: () => allLists(userId)
	});

const toggleFavourite = async ({
	listId,
	userId,
	add
}: {
	listId: number;
	userId: string;
	add: boolean;
}) => {
	if (add) {
		const response = await createFavoriteList(listId, userId);

		if (!response) {
			throw new Error("Can't add list to favourites.");
		}
	} else {
		const response = await deleteFavoriteListByUserAndList(listId, userId);

		if (!response) {
			throw new Error("Can't remove list from favourites.");
		}
	}
	return { listId, userId, add };
};

export const useToggleFavoritMutation = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: toggleFavourite,
		onSuccess: (retVal: { listId: number; userId: string; add: boolean }) => {
			toast.success(
				retVal.add
					? `List ${retVal.listId} added to favourites.`
					: `List ${retVal.listId} removed from favourites.`
			);

			return queryClient.invalidateQueries({
				queryKey: ['lists', retVal.userId]
			});
		}
	});
};

const addMovieList = async ({
	name,
	userId
}: {
	name: string;
	userId: string;
}) => {
	const result = await createMovieList(name, userId);

	if (!result.success) {
		throw new Error(`Failed to create a new list ${name}.`);
	}

	return { name, userId };
};

export const useAddMovieListMutation = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: addMovieList,
		onSuccess: (retVal: { name: string; userId: string }) => {
			toast.success(`List ${retVal.name} created successfuly.`);

			return queryClient.invalidateQueries({
				queryKey: ['created', retVal.userId]
			});
		}
	});
};

const deleteList = async ({
	listId,
	userId
}: {
	listId: number;
	userId: string;
}) => {
	const result = await deleteMovieList(listId);

	if (!result.success) {
		throw new Error(`Failed to delete list ${listId}.`);
	}

	return { listId, userId };
};

export const useDeleteMovieListMutation = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: deleteList,
		onSuccess: (retVal: { listId: number; userId: string }) => {
			toast.success(`List ${retVal.listId} deleted successfuly.`);

			return queryClient.invalidateQueries({
				queryKey: ['created', retVal.userId]
			});
		},
		onError: (err: { listId: number; userId: string }) => {
			console.error(err);
		}
	});
};

const moviesInList = async (listId: number) => {
	const data = await getMoviesInList(listId);
	if (data) {
		return data.map(
			movie =>
				({
					movieId: movie.MovieId
				}) as MovieId
		);
	} else {
		throw new Error('Failed to fetch movies in lists');
	}
};

export const useMoviesInListQuery = (listId: number) =>
	useQuery({
		queryKey: ['movieList', listId],
		queryFn: () => moviesInList(listId)
	});

export const useMoviesInListsQuery = (userId: string, listIds?: number[]) =>
	useQueries({
		queries: listIds
			? listIds.map(listId => ({
					queryKey: ['movieList', listId, userId],
					queryFn: () => moviesInList(listId)
				}))
			: []
	});

export const useGetMoviesByIds = (movieIds?: number[]) =>
	useQueries({
		queries: movieIds
			? movieIds.map(movieId => ({
					queryKey: ['movieList', movieId],
					queryFn: () => getMovieById(movieId)
				}))
			: []
	});
