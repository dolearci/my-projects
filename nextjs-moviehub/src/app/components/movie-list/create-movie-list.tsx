'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { useSession } from 'next-auth/react';
import { FormProvider, useForm } from 'react-hook-form';
import { z } from 'zod';

import { useAddMovieListMutation } from '@/hooks/movie-list';

import Button from '../controls/button';
import { FormInput } from '../users/forms/form-input';

const movieListSchema = z.object({
	name: z.string().min(1)
});

type FormSchema = z.infer<typeof movieListSchema>;

export const MovieListCreate = () => {
	const session = useSession();
	const userId = session?.data?.user?.id;
	const { mutate, isPending } = useAddMovieListMutation();

	const form = useForm<FormSchema>({
		resolver: zodResolver(movieListSchema),
		defaultValues: {
			name: ''
		},
		mode: 'onChange'
	});

	const onSubmit = (list: FormSchema) => {
		mutate({
			name: list.name,
			userId
		});
	};

	return (
		<FormProvider {...form}>
			<form onSubmit={form.handleSubmit(onSubmit)} className="flex w-full">
				<div className="flex flex-col lg:flex-row w-full justify-between pt-2 pb-6">
					<div className="flex w-full  lg:w-1/2 justify-center">
					<FormInput
						placeholder="Name"
						name="name"
						type="text"
						className="sm:flex text-2xl lg:text-xl"
						formSchema={movieListSchema}
					/>
					</div>
					<div className="flex w-full  lg:w-1/2 justify-center">
					<Button disabled={isPending} className="w-[68%] lg:w-[98%] flex justify-center items-center">
						{' '}
						{isPending ? (
							<span className="loading loading-spinner loading-xl" />
						) : (
							'Add new list'
						)}
					</Button>
					</div>
				</div>
			</form>
		</FormProvider>
	);
};
