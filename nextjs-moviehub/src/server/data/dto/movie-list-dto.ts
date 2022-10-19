import { type Row } from '@libsql/client';

export type MovieListDTO = {
	id: number;
	name: string;
	authorId: number;
	favouriteId?: number;
};

export const MovieListToDTO = (item: Row) => ({
	id: item.ListId,
	name: item.name,
	authorId: item.CreatorId,
	favouriteId: item.FavoriteId
});
