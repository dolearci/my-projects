export type ListType = 'favourite' | 'author' | 'other';

export type MovieListItemType = {
	name: string;
	authorId: string;
	listId: number;
	listType: ListType;
};

export type MovieId = {
	movieId: number;
};
