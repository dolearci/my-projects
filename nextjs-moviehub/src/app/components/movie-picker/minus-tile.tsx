import React from 'react';

type PlusTileProps = {
	removeTile: () => void;
};
const PlusTile: React.FC<PlusTileProps> = ({ removeTile }) => (
	<button
		onClick={() => {
			removeTile();
		}}
		className=" w-[75px] h-[75px] flex justify-center items-center rounded-lg hover:bg-gray-200 shadow-lg hover:shadow-xl transition ease-in duration-100 text-[80px]"
	>
		<div className="text-center pb-2">-</div>
	</button>
);

export default PlusTile;
