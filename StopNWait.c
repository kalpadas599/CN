#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    int total_frames, frame = 0;
    int ack;
    srand(time(NULL)); // Random seed

    printf("Enter the number of frames to send: ");
    scanf("%d", &total_frames);

    printf("\n--- Stop-and-Wait ARQ Simulation ---\n");

    while (frame < total_frames) {
        printf("Sending Frame %d...\n", frame);

        int success = rand() % 10;

        if (success < 8) {
            printf("Acknowledgment for Frame %d received.\n\n", frame);
            frame++;  // Move to next frame
        } else {
            printf("Acknowledgment for Frame %d lost! Retrying...\n\n", frame);
        }
    }

    printf("\nAll frames sent and acknowledged successfully!\n");
    return 0;
}
