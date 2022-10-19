import { type HTMLProps } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { type z } from 'zod';

type FormInputProps = HTMLProps<HTMLInputElement> & {
	name: string;
	formSchema: any;
	svgElement?: React.ReactNode;
};

export const FormInput = ({
	name,
	formSchema,
	svgElement,
	...inputProps
}: FormInputProps) => {
	type FormSchema = z.infer<typeof formSchema>;

	const { watch, control, trigger } = useFormContext<FormSchema>();

	const inputValue = watch(name);

	return (
		<Controller
			control={control}
			name={name}
			render={({ field, fieldState }) => (
				<div className="flex-row">
					<label
						className="input input-bordered flex items-center gap-2 my-2"
						htmlFor={name}
					>
						{svgElement}
						<input
							className="grow"
							onChange={e => {
								field.onChange(e.target.value);
								trigger(name);
							}}
							value={inputValue}
							{...inputProps}
						/>
					</label>
					<div>
						{fieldState.error && (
							<div className="label">
								<span className="label-text-alt text-red-500">
									{fieldState.error.message}
								</span>
							</div>
						)}
					</div>
				</div>
			)}
		/>
	);
};
