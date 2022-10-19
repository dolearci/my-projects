import { type MovieListItemType } from '@/types/movie-list';

import MovieListItem from './movie-list-item';

export type MovieListProps = {
	items: MovieListItemType[];
	maxGridCols?: boolean,
	deletable: boolean;
};

const MovieList = ({ items, deletable, maxGridCols }: MovieListProps) => (
	<div className="w-full ">
		<ul className={`w-full grid grid-cols-1 md:grid-cols-2 gap-4 ${maxGridCols ? "xl:grid-cols-4": ""}`}>
			{items.map(item => (
				<MovieListItem
					key={item.listId.toString()}
					name={item.name}
					authorId={item.authorId}
					listId={item.listId}
					listType={item.listType}
					deletable={deletable}
				/>
			))}
		</ul>
	</div>
);

export default MovieList;
