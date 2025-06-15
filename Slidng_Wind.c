#include <stdio.h>

int main()
{
    int window_size, num_frames, frames[50];

    printf("Enter window size: ");
    scanf("%d", &window_size);

    printf("Enter number of frames to transmit: ");
    scanf("%d", &num_frames);

    printf("Enter %d frame values:\n", num_frames);
    for (int i = 0; i < num_frames; i++)
    {
        printf("Frame[%d]: ", i);
        scanf("%d", &frames[i]);
    }

    printf("\n--- Sliding Window Protocol Simulation ---\n");
    printf("Sending frames in windows of size %d\n\n", window_size);

    for (int i = 0; i < num_frames; i++)
    {
        // Send window
        printf("Sending window starting at Frame[%d]:\n", i);
        for (int j = i; j < i + window_size && j < num_frames; j++)
        {
            printf("  Sent Frame[%d] -> %d\n", j, frames[j]);
        }

        printf("Waiting for Acknowledgment...\n");
        printf("Acknowledgment received for frames %d to %d\n\n", i, ((i + window_size - 1) < num_frames - 1 ? (i + window_size - 1) : num_frames - 1));

        i += window_size - 1; // Move the window
    }

    printf("\nAll frames transmitted successfully!\n");
    return 0;
}
