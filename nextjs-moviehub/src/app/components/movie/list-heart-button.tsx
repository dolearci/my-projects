'use-client';
import React from 'react';
import { FaHeart } from 'react-icons/fa';

type ListHeartButtonProps = {
	onClick: () => void;
	className?: string;
};

const ListHeartButton: React.FC<ListHeartButtonProps> = ({
	onClick,
	className
}) => {
	const defaultClassName =
		'transition-colors duration-300 hover:text-red-500 text-gray-300';

	return (
		<button className="p-2" onClick={onClick}>
			<FaHeart size={45} className={className ? className : defaultClassName} />
		</button>
	);
};

export default ListHeartButton;
