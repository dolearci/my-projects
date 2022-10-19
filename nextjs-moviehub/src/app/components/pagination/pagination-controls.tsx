'use client';

import { useRouter, useSearchParams } from 'next/navigation';

import Button from '../controls/button';

type PaginationControlsProps = {
	baseUrl: string;
};

const PaginationControls: React.FC<PaginationControlsProps> = ({ baseUrl }) => {
	const router = useRouter();
	const searchParams = useSearchParams();

	const page = searchParams?.get('page') ?? '1';
	const per_page = searchParams?.get('per_page') ?? '12';

	const hasPrevPage = page === '1';
	const hasNextPage = page === '20';

	return (
		<div className="flex gap-2 w-full justify-center p-10">
			<div className="flex justify-center">
				<Button
					className="w-[120px]"
					disabled={hasPrevPage}
					onClick={() => {
						router.push(
							`${baseUrl}/?page=${Number(page) - 1}&per_page=${per_page}`
						);
					}}
				>
					previous
				</Button>

				<div className="flex justify-center items-center ">
					<span className="text-lg px-10">{page} / 20</span>
				</div>

				<Button
					className="w-[120px]"
					disabled={hasNextPage}
					onClick={() => {
						router.push(
							`best-of/?page=${Number(page) + 1}&per_page=${per_page}`
						);
					}}
				>
					next
				</Button>
			</div>
		</div>
	);
};

export default PaginationControls;
