#define BLOCK_SIZE 32

__global__ void calculate(sGalaxy A, sGalaxy B, int n, float* out){
    
    unsigned i = blockIdx.x * blockDim.x + threadIdx.x;
    __shared__ float Asx[BLOCK_SIZE];
    __shared__ float Asy[BLOCK_SIZE];
    __shared__ float Asz[BLOCK_SIZE];
    __shared__ float Bsx[BLOCK_SIZE];
    __shared__ float Bsy[BLOCK_SIZE];
    __shared__ float Bsz[BLOCK_SIZE];

    if(i > n-1) return;
    
    float tmp = 0.0f;

    Asx[threadIdx.x] = A.x[threadIdx.x];
    Asy[threadIdx.x] = A.y[threadIdx.x];
    Asz[threadIdx.x] = A.z[threadIdx.x];
    Bsx[threadIdx.x] = B.x[threadIdx.x];
    Bsy[threadIdx.x] = B.y[threadIdx.x];
    Bsz[threadIdx.x] = B.z[threadIdx.x];
    __syncthreads();

    for(int idx = threadIdx.x + 1; idx < BLOCK_SIZE && idx < n; idx++)
    {   
        float da = sqrt((Asx[idx]-A.x[i])*(Asx[idx]-A.x[i])
            + (Asy[idx]-A.y[i])*(Asy[idx]-A.y[i])
            + (Asz[idx]-A.z[i])*(Asz[idx]-A.z[i]));
        float db = sqrt((Bsx[idx]-B.x[i])*(Bsx[idx]-B.x[i])
            + (Bsy[idx]-B.y[i])*(Bsy[idx]-B.y[i])
            + (Bsz[idx]-B.z[i])*(Bsz[idx]-B.z[i]));
        tmp += (da-db) * (da-db);
    }

    for(int b = blockIdx.x + 1; b < (n / BLOCK_SIZE)-1; b++)
    {
        Asx[threadIdx.x] = A.x[threadIdx.x + b * BLOCK_SIZE];
        Asy[threadIdx.x] = A.y[threadIdx.x + b * BLOCK_SIZE];
        Asz[threadIdx.x] = A.z[threadIdx.x + b * BLOCK_SIZE];
        Bsx[threadIdx.x] = B.x[threadIdx.x + b * BLOCK_SIZE];
        Bsy[threadIdx.x] = B.y[threadIdx.x + b * BLOCK_SIZE];
        Bsz[threadIdx.x] = B.z[threadIdx.x + b * BLOCK_SIZE];
        __syncthreads();
        for(int idx = 0 ; idx < BLOCK_SIZE; idx++)
        {
            float da = sqrt((Asx[idx]-A.x[i])*(Asx[idx]-A.x[i])
                + (Asy[idx]-A.y[i])*(Asy[idx]-A.y[i])
                + (Asz[idx]-A.z[i])*(Asz[idx]-A.z[i]));
            float db = sqrt((Bsx[idx]-B.x[i])*(Bsx[idx]-B.x[i])
                + (Bsy[idx]-B.y[i])*(Bsy[idx]-B.y[i])
                + (Bsz[idx]-B.z[i])*(Bsz[idx]-B.z[i]));
            tmp += (da-db) * (da-db);
        }	
	}

    Asx[threadIdx.x] = A.x[threadIdx.x + ((n / BLOCK_SIZE) * BLOCK_SIZE)];
    Asy[threadIdx.x] = A.y[threadIdx.x + ((n / BLOCK_SIZE) * BLOCK_SIZE)];
    Asz[threadIdx.x] = A.z[threadIdx.x + ((n / BLOCK_SIZE) * BLOCK_SIZE)];
    Bsx[threadIdx.x] = B.x[threadIdx.x + ((n / BLOCK_SIZE) * BLOCK_SIZE)];
    Bsy[threadIdx.x] = B.y[threadIdx.x + ((n / BLOCK_SIZE) * BLOCK_SIZE)];
    Bsz[threadIdx.x] = B.z[threadIdx.x + ((n / BLOCK_SIZE) * BLOCK_SIZE)];
    __syncthreads();

    for(int idx = 0; idx < BLOCK_SIZE && 
    (idx + (((n / BLOCK_SIZE)) * BLOCK_SIZE) ) < n; idx++)
    {
        float da = sqrt((Asx[idx]-A.x[i])*(Asx[idx]-A.x[i])
            + (Asy[idx]-A.y[i])*(Asy[idx]-A.y[i])
            + (Asz[idx]-A.z[i])*(Asz[idx]-A.z[i]));
        float db = sqrt((Bsx[idx]-B.x[i])*(Bsx[idx]-B.x[i])
            + (Bsy[idx]-B.y[i])*(Bsy[idx]-B.y[i])
            + (Bsz[idx]-B.z[i])*(Bsz[idx]-B.z[i]));
        tmp += (da-db) * (da-db);
    }
    out[i] = tmp;
}



float solveGPU(sGalaxy A, sGalaxy B, int n) {
    int vector_size = n;
    float *d_diff, *diff;

    cudaMallocHost((void **) &diff, vector_size * sizeof(float)); 
    
    if (cudaMalloc((void **) &d_diff, vector_size * sizeof(d_diff[0])) != cudaSuccess) {
		fprintf(stderr, "Device memory allocation error!\n");
		cudaFree(d_diff);
	}

    int blocksPerGrid = (n + BLOCK_SIZE - 1) / BLOCK_SIZE;
    calculate<<< blocksPerGrid,  BLOCK_SIZE>>>(A, B, n, d_diff);
    

    cudaMemcpy(diff, d_diff, vector_size * sizeof(d_diff[0]), cudaMemcpyDeviceToHost);
    
    float diff_result = 0.0f;
    
    for (int i = 0; i < vector_size; i++){
        diff_result += diff[i];
    }  

    if (d_diff) cudaFree(d_diff);
	if (diff) cudaFreeHost(diff);

    return sqrt(1/((float)n*((float)n-1)) * diff_result);
}