'use-client';

import React from 'react';
import { FaTrash } from 'react-icons/fa';

import { useDeleteMovieListMutation } from '@/hooks/movie-list';

type DeleteListButtonProps = {
	listId: number;
	userId: string;
};

const DeleteListButton: React.FC<DeleteListButtonProps> = ({
	listId,
	userId
}) => {
	const { mutate, isPending } = useDeleteMovieListMutation();

	return (
		<button
			className="p-2"
			onClick={() => {
				mutate({
					listId,
					userId
				});
			}}
		>
			{isPending ? (
				<span className="loading loading-spinner loading-2xl" />
			) : (
				<FaTrash size={40} />
			)}
		</button>
	);
};

export default DeleteListButton;
