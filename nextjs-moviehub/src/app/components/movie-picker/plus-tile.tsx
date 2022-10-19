import React from 'react';

type PlusTileProps = {
	addTile: () => void;
};
const PlusTile: React.FC<PlusTileProps> = ({ addTile }) => (
	<button
		onClick={() => {
			addTile();
		}}
		className=" w-[350px] h-full m-2 pb-2 flex justify-center items-center rounded-lg hover:bg-gray-200 shadow-md hover:shadow-xl transition ease-in duration-100 text-[100px]"
	>
		<div className="m-5  flex justify-center items-center">+</div>
	</button>
);

export default PlusTile;
