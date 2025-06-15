#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function to input frames
void input(int a[], int frame_size)
{
    printf("\nInput Frames:\n");
    for (int i = 0; i < frame_size; i++)
    {
        printf("Enter value for Frame[%d]: ", i);
        scanf("%d", &a[i]);
    }
}

// Function to display frames
void display(int a[], int frame_size)
{
    printf("\nAll Frames:\n");
    for (int i = 0; i < frame_size; i++)
    {
        printf("Frame[%d]: %d\n", i, a[i]);
    }
}

// Simulate Selective Repeat ARQ
void selective_repeat(int frames[], int window_size, int frame_size)
{
    int total_transmissions = 0;
    int ack[50] = {0}; // Acknowledgment flags
    int i = 0;

    while (i < frame_size)
    {
        printf("\n--- Sending Window (Frames %d to %d) ---\n", i, (i + window_size - 1 < frame_size ? i + window_size - 1 : frame_size - 1));

        // Send frames in the current window
        for (int j = i; j < i + window_size && j < frame_size; j++)
        {
            if (!ack[j])
            {
                printf("Sent Frame[%d]: %d\n", j, frames[j]);
                total_transmissions++;
            }
        }

        // Simulate ACKs
        for (int j = i; j < i + window_size && j < frame_size; j++)
        {
            if (!ack[j])
            {
                int success = rand() % 2;
                if (success)
                {
                    printf("Frame[%d]: Acknowledged\n", j);
                    ack[j] = 1;
                }
                else
                {
                    printf("Frame[%d]: Not Acknowledged (Will retry)\n", j);
                }
            }
        }

        // Slide window to next unacknowledged frame
        while (i < frame_size && ack[i])
        {
            i++;
        }
    }

    printf("\nTotal Transmissions (including retransmissions): %d\n", total_transmissions);
}

int main()
{
    int frames[50];
    int window_size, frame_size;

    srand(time(NULL)); // Seed for randomness

    printf("Selective Repeat ARQ Simulation\n");
    printf("-------------------------------\n");

    printf("Enter Window Size: ");
    scanf("%d", &window_size);

    printf("Enter Number of Frames to Transmit: ");
    scanf("%d", &frame_size);

    input(frames, frame_size);
    display(frames, frame_size);
    selective_repeat(frames, window_size, frame_size);

    return 0;
}
